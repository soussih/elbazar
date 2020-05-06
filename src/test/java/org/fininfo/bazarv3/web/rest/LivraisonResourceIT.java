package org.fininfo.bazarv3.web.rest;

import org.fininfo.bazarv3.Bazarv3App;
import org.fininfo.bazarv3.domain.Livraison;
import org.fininfo.bazarv3.repository.LivraisonRepository;
import org.fininfo.bazarv3.repository.search.LivraisonSearchRepository;
import org.fininfo.bazarv3.service.LivraisonService;
import org.fininfo.bazarv3.service.dto.LivraisonDTO;
import org.fininfo.bazarv3.service.mapper.LivraisonMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.fininfo.bazarv3.domain.enumeration.CatClient;
/**
 * Integration tests for the {@link LivraisonResource} REST controller.
 */
@SpringBootTest(classes = Bazarv3App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class LivraisonResourceIT {

    private static final CatClient DEFAULT_CATEGORIE_CLIENT = CatClient.Silver;
    private static final CatClient UPDATED_CATEGORIE_CLIENT = CatClient.Gold;

    private static final Double DEFAULT_INTERVAL_VALEUR = 1D;
    private static final Double UPDATED_INTERVAL_VALEUR = 2D;

    private static final Double DEFAULT_FRAIS = 1D;
    private static final Double UPDATED_FRAIS = 2D;

    private static final LocalDate DEFAULT_CREE_LE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREE_LE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREE_PAR = 1;
    private static final Integer UPDATED_CREE_PAR = 2;

    private static final LocalDate DEFAULT_MODIFIE_LE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIE_LE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_MODIFIE_PAR = 1;
    private static final Integer UPDATED_MODIFIE_PAR = 2;

    @Autowired
    private LivraisonRepository livraisonRepository;

    @Autowired
    private LivraisonMapper livraisonMapper;

    @Autowired
    private LivraisonService livraisonService;

    /**
     * This repository is mocked in the org.fininfo.bazarv3.repository.search test package.
     *
     * @see org.fininfo.bazarv3.repository.search.LivraisonSearchRepositoryMockConfiguration
     */
    @Autowired
    private LivraisonSearchRepository mockLivraisonSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLivraisonMockMvc;

    private Livraison livraison;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livraison createEntity(EntityManager em) {
        Livraison livraison = new Livraison()
            .categorieClient(DEFAULT_CATEGORIE_CLIENT)
            .intervalValeur(DEFAULT_INTERVAL_VALEUR)
            .frais(DEFAULT_FRAIS)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR);
        return livraison;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livraison createUpdatedEntity(EntityManager em) {
        Livraison livraison = new Livraison()
            .categorieClient(UPDATED_CATEGORIE_CLIENT)
            .intervalValeur(UPDATED_INTERVAL_VALEUR)
            .frais(UPDATED_FRAIS)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        return livraison;
    }

    @BeforeEach
    public void initTest() {
        livraison = createEntity(em);
    }

    @Test
    @Transactional
    public void createLivraison() throws Exception {
        int databaseSizeBeforeCreate = livraisonRepository.findAll().size();

        // Create the Livraison
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);
        restLivraisonMockMvc.perform(post("/api/livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livraisonDTO)))
            .andExpect(status().isCreated());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeCreate + 1);
        Livraison testLivraison = livraisonList.get(livraisonList.size() - 1);
        assertThat(testLivraison.getCategorieClient()).isEqualTo(DEFAULT_CATEGORIE_CLIENT);
        assertThat(testLivraison.getIntervalValeur()).isEqualTo(DEFAULT_INTERVAL_VALEUR);
        assertThat(testLivraison.getFrais()).isEqualTo(DEFAULT_FRAIS);
        assertThat(testLivraison.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testLivraison.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testLivraison.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testLivraison.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);

        // Validate the Livraison in Elasticsearch
        verify(mockLivraisonSearchRepository, times(1)).save(testLivraison);
    }

    @Test
    @Transactional
    public void createLivraisonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = livraisonRepository.findAll().size();

        // Create the Livraison with an existing ID
        livraison.setId(1L);
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivraisonMockMvc.perform(post("/api/livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livraisonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeCreate);

        // Validate the Livraison in Elasticsearch
        verify(mockLivraisonSearchRepository, times(0)).save(livraison);
    }


    @Test
    @Transactional
    public void getAllLivraisons() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get all the livraisonList
        restLivraisonMockMvc.perform(get("/api/livraisons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livraison.getId().intValue())))
            .andExpect(jsonPath("$.[*].categorieClient").value(hasItem(DEFAULT_CATEGORIE_CLIENT.toString())))
            .andExpect(jsonPath("$.[*].intervalValeur").value(hasItem(DEFAULT_INTERVAL_VALEUR.doubleValue())))
            .andExpect(jsonPath("$.[*].frais").value(hasItem(DEFAULT_FRAIS.doubleValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
    
    @Test
    @Transactional
    public void getLivraison() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        // Get the livraison
        restLivraisonMockMvc.perform(get("/api/livraisons/{id}", livraison.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(livraison.getId().intValue()))
            .andExpect(jsonPath("$.categorieClient").value(DEFAULT_CATEGORIE_CLIENT.toString()))
            .andExpect(jsonPath("$.intervalValeur").value(DEFAULT_INTERVAL_VALEUR.doubleValue()))
            .andExpect(jsonPath("$.frais").value(DEFAULT_FRAIS.doubleValue()))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR));
    }

    @Test
    @Transactional
    public void getNonExistingLivraison() throws Exception {
        // Get the livraison
        restLivraisonMockMvc.perform(get("/api/livraisons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLivraison() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        int databaseSizeBeforeUpdate = livraisonRepository.findAll().size();

        // Update the livraison
        Livraison updatedLivraison = livraisonRepository.findById(livraison.getId()).get();
        // Disconnect from session so that the updates on updatedLivraison are not directly saved in db
        em.detach(updatedLivraison);
        updatedLivraison
            .categorieClient(UPDATED_CATEGORIE_CLIENT)
            .intervalValeur(UPDATED_INTERVAL_VALEUR)
            .frais(UPDATED_FRAIS)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(updatedLivraison);

        restLivraisonMockMvc.perform(put("/api/livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livraisonDTO)))
            .andExpect(status().isOk());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeUpdate);
        Livraison testLivraison = livraisonList.get(livraisonList.size() - 1);
        assertThat(testLivraison.getCategorieClient()).isEqualTo(UPDATED_CATEGORIE_CLIENT);
        assertThat(testLivraison.getIntervalValeur()).isEqualTo(UPDATED_INTERVAL_VALEUR);
        assertThat(testLivraison.getFrais()).isEqualTo(UPDATED_FRAIS);
        assertThat(testLivraison.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testLivraison.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testLivraison.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testLivraison.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);

        // Validate the Livraison in Elasticsearch
        verify(mockLivraisonSearchRepository, times(1)).save(testLivraison);
    }

    @Test
    @Transactional
    public void updateNonExistingLivraison() throws Exception {
        int databaseSizeBeforeUpdate = livraisonRepository.findAll().size();

        // Create the Livraison
        LivraisonDTO livraisonDTO = livraisonMapper.toDto(livraison);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivraisonMockMvc.perform(put("/api/livraisons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livraisonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Livraison in the database
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Livraison in Elasticsearch
        verify(mockLivraisonSearchRepository, times(0)).save(livraison);
    }

    @Test
    @Transactional
    public void deleteLivraison() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);

        int databaseSizeBeforeDelete = livraisonRepository.findAll().size();

        // Delete the livraison
        restLivraisonMockMvc.perform(delete("/api/livraisons/{id}", livraison.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Livraison> livraisonList = livraisonRepository.findAll();
        assertThat(livraisonList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Livraison in Elasticsearch
        verify(mockLivraisonSearchRepository, times(1)).deleteById(livraison.getId());
    }

    @Test
    @Transactional
    public void searchLivraison() throws Exception {
        // Initialize the database
        livraisonRepository.saveAndFlush(livraison);
        when(mockLivraisonSearchRepository.search(queryStringQuery("id:" + livraison.getId())))
            .thenReturn(Collections.singletonList(livraison));
        // Search the livraison
        restLivraisonMockMvc.perform(get("/api/_search/livraisons?query=id:" + livraison.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livraison.getId().intValue())))
            .andExpect(jsonPath("$.[*].categorieClient").value(hasItem(DEFAULT_CATEGORIE_CLIENT.toString())))
            .andExpect(jsonPath("$.[*].intervalValeur").value(hasItem(DEFAULT_INTERVAL_VALEUR.doubleValue())))
            .andExpect(jsonPath("$.[*].frais").value(hasItem(DEFAULT_FRAIS.doubleValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
}
