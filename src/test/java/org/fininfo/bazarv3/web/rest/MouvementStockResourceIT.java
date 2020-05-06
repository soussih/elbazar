package org.fininfo.bazarv3.web.rest;

import org.fininfo.bazarv3.Bazarv3App;
import org.fininfo.bazarv3.domain.MouvementStock;
import org.fininfo.bazarv3.repository.MouvementStockRepository;
import org.fininfo.bazarv3.repository.search.MouvementStockSearchRepository;
import org.fininfo.bazarv3.service.MouvementStockService;
import org.fininfo.bazarv3.service.dto.MouvementStockDTO;
import org.fininfo.bazarv3.service.mapper.MouvementStockMapper;

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

import org.fininfo.bazarv3.domain.enumeration.TypeMvt;
/**
 * Integration tests for the {@link MouvementStockResource} REST controller.
 */
@SpringBootTest(classes = Bazarv3App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MouvementStockResourceIT {

    private static final TypeMvt DEFAULT_TYPE = TypeMvt.Stock;
    private static final TypeMvt UPDATED_TYPE = TypeMvt.Commande;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_SENS = 1;
    private static final Integer UPDATED_SENS = 2;

    private static final Double DEFAULT_QUANTITE = 1D;
    private static final Double UPDATED_QUANTITE = 2D;

    private static final LocalDate DEFAULT_CREE_LE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREE_LE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREE_PAR = 1;
    private static final Integer UPDATED_CREE_PAR = 2;

    private static final LocalDate DEFAULT_MODIFIE_LE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIE_LE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_MODIFIE_PAR = 1;
    private static final Integer UPDATED_MODIFIE_PAR = 2;

    @Autowired
    private MouvementStockRepository mouvementStockRepository;

    @Autowired
    private MouvementStockMapper mouvementStockMapper;

    @Autowired
    private MouvementStockService mouvementStockService;

    /**
     * This repository is mocked in the org.fininfo.bazarv3.repository.search test package.
     *
     * @see org.fininfo.bazarv3.repository.search.MouvementStockSearchRepositoryMockConfiguration
     */
    @Autowired
    private MouvementStockSearchRepository mockMouvementStockSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMouvementStockMockMvc;

    private MouvementStock mouvementStock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MouvementStock createEntity(EntityManager em) {
        MouvementStock mouvementStock = new MouvementStock()
            .type(DEFAULT_TYPE)
            .date(DEFAULT_DATE)
            .sens(DEFAULT_SENS)
            .quantite(DEFAULT_QUANTITE)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR);
        return mouvementStock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MouvementStock createUpdatedEntity(EntityManager em) {
        MouvementStock mouvementStock = new MouvementStock()
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .sens(UPDATED_SENS)
            .quantite(UPDATED_QUANTITE)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        return mouvementStock;
    }

    @BeforeEach
    public void initTest() {
        mouvementStock = createEntity(em);
    }

    @Test
    @Transactional
    public void createMouvementStock() throws Exception {
        int databaseSizeBeforeCreate = mouvementStockRepository.findAll().size();

        // Create the MouvementStock
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);
        restMouvementStockMockMvc.perform(post("/api/mouvement-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvementStockDTO)))
            .andExpect(status().isCreated());

        // Validate the MouvementStock in the database
        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeCreate + 1);
        MouvementStock testMouvementStock = mouvementStockList.get(mouvementStockList.size() - 1);
        assertThat(testMouvementStock.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMouvementStock.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMouvementStock.getSens()).isEqualTo(DEFAULT_SENS);
        assertThat(testMouvementStock.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testMouvementStock.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testMouvementStock.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testMouvementStock.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testMouvementStock.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);

        // Validate the MouvementStock in Elasticsearch
        verify(mockMouvementStockSearchRepository, times(1)).save(testMouvementStock);
    }

    @Test
    @Transactional
    public void createMouvementStockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mouvementStockRepository.findAll().size();

        // Create the MouvementStock with an existing ID
        mouvementStock.setId(1L);
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMouvementStockMockMvc.perform(post("/api/mouvement-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvementStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MouvementStock in the database
        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeCreate);

        // Validate the MouvementStock in Elasticsearch
        verify(mockMouvementStockSearchRepository, times(0)).save(mouvementStock);
    }


    @Test
    @Transactional
    public void getAllMouvementStocks() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList
        restMouvementStockMockMvc.perform(get("/api/mouvement-stocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mouvementStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].sens").value(hasItem(DEFAULT_SENS)))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
    
    @Test
    @Transactional
    public void getMouvementStock() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get the mouvementStock
        restMouvementStockMockMvc.perform(get("/api/mouvement-stocks/{id}", mouvementStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mouvementStock.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.sens").value(DEFAULT_SENS))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.doubleValue()))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR));
    }

    @Test
    @Transactional
    public void getNonExistingMouvementStock() throws Exception {
        // Get the mouvementStock
        restMouvementStockMockMvc.perform(get("/api/mouvement-stocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMouvementStock() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        int databaseSizeBeforeUpdate = mouvementStockRepository.findAll().size();

        // Update the mouvementStock
        MouvementStock updatedMouvementStock = mouvementStockRepository.findById(mouvementStock.getId()).get();
        // Disconnect from session so that the updates on updatedMouvementStock are not directly saved in db
        em.detach(updatedMouvementStock);
        updatedMouvementStock
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .sens(UPDATED_SENS)
            .quantite(UPDATED_QUANTITE)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(updatedMouvementStock);

        restMouvementStockMockMvc.perform(put("/api/mouvement-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvementStockDTO)))
            .andExpect(status().isOk());

        // Validate the MouvementStock in the database
        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeUpdate);
        MouvementStock testMouvementStock = mouvementStockList.get(mouvementStockList.size() - 1);
        assertThat(testMouvementStock.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMouvementStock.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMouvementStock.getSens()).isEqualTo(UPDATED_SENS);
        assertThat(testMouvementStock.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testMouvementStock.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testMouvementStock.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testMouvementStock.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testMouvementStock.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);

        // Validate the MouvementStock in Elasticsearch
        verify(mockMouvementStockSearchRepository, times(1)).save(testMouvementStock);
    }

    @Test
    @Transactional
    public void updateNonExistingMouvementStock() throws Exception {
        int databaseSizeBeforeUpdate = mouvementStockRepository.findAll().size();

        // Create the MouvementStock
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMouvementStockMockMvc.perform(put("/api/mouvement-stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvementStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MouvementStock in the database
        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MouvementStock in Elasticsearch
        verify(mockMouvementStockSearchRepository, times(0)).save(mouvementStock);
    }

    @Test
    @Transactional
    public void deleteMouvementStock() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        int databaseSizeBeforeDelete = mouvementStockRepository.findAll().size();

        // Delete the mouvementStock
        restMouvementStockMockMvc.perform(delete("/api/mouvement-stocks/{id}", mouvementStock.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MouvementStock> mouvementStockList = mouvementStockRepository.findAll();
        assertThat(mouvementStockList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MouvementStock in Elasticsearch
        verify(mockMouvementStockSearchRepository, times(1)).deleteById(mouvementStock.getId());
    }

    @Test
    @Transactional
    public void searchMouvementStock() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);
        when(mockMouvementStockSearchRepository.search(queryStringQuery("id:" + mouvementStock.getId())))
            .thenReturn(Collections.singletonList(mouvementStock));
        // Search the mouvementStock
        restMouvementStockMockMvc.perform(get("/api/_search/mouvement-stocks?query=id:" + mouvementStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mouvementStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].sens").value(hasItem(DEFAULT_SENS)))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
}
