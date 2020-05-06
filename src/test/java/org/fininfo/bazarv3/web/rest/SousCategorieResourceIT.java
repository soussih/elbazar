package org.fininfo.bazarv3.web.rest;

import org.fininfo.bazarv3.Bazarv3App;
import org.fininfo.bazarv3.domain.SousCategorie;
import org.fininfo.bazarv3.repository.SousCategorieRepository;
import org.fininfo.bazarv3.repository.search.SousCategorieSearchRepository;
import org.fininfo.bazarv3.service.SousCategorieService;
import org.fininfo.bazarv3.service.dto.SousCategorieDTO;
import org.fininfo.bazarv3.service.mapper.SousCategorieMapper;

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

/**
 * Integration tests for the {@link SousCategorieResource} REST controller.
 */
@SpringBootTest(classes = Bazarv3App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SousCategorieResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final Boolean DEFAULT_ETAT = false;
    private static final Boolean UPDATED_ETAT = true;

    private static final LocalDate DEFAULT_CREE_LE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREE_LE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREE_PAR = 1;
    private static final Integer UPDATED_CREE_PAR = 2;

    private static final LocalDate DEFAULT_MODIFIE_LE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIE_LE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_MODIFIE_PAR = 1;
    private static final Integer UPDATED_MODIFIE_PAR = 2;

    @Autowired
    private SousCategorieRepository sousCategorieRepository;

    @Autowired
    private SousCategorieMapper sousCategorieMapper;

    @Autowired
    private SousCategorieService sousCategorieService;

    /**
     * This repository is mocked in the org.fininfo.bazarv3.repository.search test package.
     *
     * @see org.fininfo.bazarv3.repository.search.SousCategorieSearchRepositoryMockConfiguration
     */
    @Autowired
    private SousCategorieSearchRepository mockSousCategorieSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSousCategorieMockMvc;

    private SousCategorie sousCategorie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SousCategorie createEntity(EntityManager em) {
        SousCategorie sousCategorie = new SousCategorie()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .position(DEFAULT_POSITION)
            .etat(DEFAULT_ETAT)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR);
        return sousCategorie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SousCategorie createUpdatedEntity(EntityManager em) {
        SousCategorie sousCategorie = new SousCategorie()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .position(UPDATED_POSITION)
            .etat(UPDATED_ETAT)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        return sousCategorie;
    }

    @BeforeEach
    public void initTest() {
        sousCategorie = createEntity(em);
    }

    @Test
    @Transactional
    public void createSousCategorie() throws Exception {
        int databaseSizeBeforeCreate = sousCategorieRepository.findAll().size();

        // Create the SousCategorie
        SousCategorieDTO sousCategorieDTO = sousCategorieMapper.toDto(sousCategorie);
        restSousCategorieMockMvc.perform(post("/api/sous-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sousCategorieDTO)))
            .andExpect(status().isCreated());

        // Validate the SousCategorie in the database
        List<SousCategorie> sousCategorieList = sousCategorieRepository.findAll();
        assertThat(sousCategorieList).hasSize(databaseSizeBeforeCreate + 1);
        SousCategorie testSousCategorie = sousCategorieList.get(sousCategorieList.size() - 1);
        assertThat(testSousCategorie.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSousCategorie.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSousCategorie.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testSousCategorie.isEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testSousCategorie.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testSousCategorie.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testSousCategorie.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testSousCategorie.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);

        // Validate the SousCategorie in Elasticsearch
        verify(mockSousCategorieSearchRepository, times(1)).save(testSousCategorie);
    }

    @Test
    @Transactional
    public void createSousCategorieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sousCategorieRepository.findAll().size();

        // Create the SousCategorie with an existing ID
        sousCategorie.setId(1L);
        SousCategorieDTO sousCategorieDTO = sousCategorieMapper.toDto(sousCategorie);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSousCategorieMockMvc.perform(post("/api/sous-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sousCategorieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SousCategorie in the database
        List<SousCategorie> sousCategorieList = sousCategorieRepository.findAll();
        assertThat(sousCategorieList).hasSize(databaseSizeBeforeCreate);

        // Validate the SousCategorie in Elasticsearch
        verify(mockSousCategorieSearchRepository, times(0)).save(sousCategorie);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = sousCategorieRepository.findAll().size();
        // set the field null
        sousCategorie.setNom(null);

        // Create the SousCategorie, which fails.
        SousCategorieDTO sousCategorieDTO = sousCategorieMapper.toDto(sousCategorie);

        restSousCategorieMockMvc.perform(post("/api/sous-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sousCategorieDTO)))
            .andExpect(status().isBadRequest());

        List<SousCategorie> sousCategorieList = sousCategorieRepository.findAll();
        assertThat(sousCategorieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sousCategorieRepository.findAll().size();
        // set the field null
        sousCategorie.setPosition(null);

        // Create the SousCategorie, which fails.
        SousCategorieDTO sousCategorieDTO = sousCategorieMapper.toDto(sousCategorie);

        restSousCategorieMockMvc.perform(post("/api/sous-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sousCategorieDTO)))
            .andExpect(status().isBadRequest());

        List<SousCategorie> sousCategorieList = sousCategorieRepository.findAll();
        assertThat(sousCategorieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSousCategories() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get all the sousCategorieList
        restSousCategorieMockMvc.perform(get("/api/sous-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sousCategorie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
    
    @Test
    @Transactional
    public void getSousCategorie() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        // Get the sousCategorie
        restSousCategorieMockMvc.perform(get("/api/sous-categories/{id}", sousCategorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sousCategorie.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.booleanValue()))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR));
    }

    @Test
    @Transactional
    public void getNonExistingSousCategorie() throws Exception {
        // Get the sousCategorie
        restSousCategorieMockMvc.perform(get("/api/sous-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSousCategorie() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        int databaseSizeBeforeUpdate = sousCategorieRepository.findAll().size();

        // Update the sousCategorie
        SousCategorie updatedSousCategorie = sousCategorieRepository.findById(sousCategorie.getId()).get();
        // Disconnect from session so that the updates on updatedSousCategorie are not directly saved in db
        em.detach(updatedSousCategorie);
        updatedSousCategorie
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .position(UPDATED_POSITION)
            .etat(UPDATED_ETAT)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        SousCategorieDTO sousCategorieDTO = sousCategorieMapper.toDto(updatedSousCategorie);

        restSousCategorieMockMvc.perform(put("/api/sous-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sousCategorieDTO)))
            .andExpect(status().isOk());

        // Validate the SousCategorie in the database
        List<SousCategorie> sousCategorieList = sousCategorieRepository.findAll();
        assertThat(sousCategorieList).hasSize(databaseSizeBeforeUpdate);
        SousCategorie testSousCategorie = sousCategorieList.get(sousCategorieList.size() - 1);
        assertThat(testSousCategorie.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSousCategorie.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSousCategorie.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testSousCategorie.isEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testSousCategorie.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testSousCategorie.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testSousCategorie.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testSousCategorie.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);

        // Validate the SousCategorie in Elasticsearch
        verify(mockSousCategorieSearchRepository, times(1)).save(testSousCategorie);
    }

    @Test
    @Transactional
    public void updateNonExistingSousCategorie() throws Exception {
        int databaseSizeBeforeUpdate = sousCategorieRepository.findAll().size();

        // Create the SousCategorie
        SousCategorieDTO sousCategorieDTO = sousCategorieMapper.toDto(sousCategorie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSousCategorieMockMvc.perform(put("/api/sous-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sousCategorieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SousCategorie in the database
        List<SousCategorie> sousCategorieList = sousCategorieRepository.findAll();
        assertThat(sousCategorieList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SousCategorie in Elasticsearch
        verify(mockSousCategorieSearchRepository, times(0)).save(sousCategorie);
    }

    @Test
    @Transactional
    public void deleteSousCategorie() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);

        int databaseSizeBeforeDelete = sousCategorieRepository.findAll().size();

        // Delete the sousCategorie
        restSousCategorieMockMvc.perform(delete("/api/sous-categories/{id}", sousCategorie.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SousCategorie> sousCategorieList = sousCategorieRepository.findAll();
        assertThat(sousCategorieList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SousCategorie in Elasticsearch
        verify(mockSousCategorieSearchRepository, times(1)).deleteById(sousCategorie.getId());
    }

    @Test
    @Transactional
    public void searchSousCategorie() throws Exception {
        // Initialize the database
        sousCategorieRepository.saveAndFlush(sousCategorie);
        when(mockSousCategorieSearchRepository.search(queryStringQuery("id:" + sousCategorie.getId())))
            .thenReturn(Collections.singletonList(sousCategorie));
        // Search the sousCategorie
        restSousCategorieMockMvc.perform(get("/api/_search/sous-categories?query=id:" + sousCategorie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sousCategorie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.booleanValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
}
