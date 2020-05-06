package org.fininfo.bazarv3.web.rest;

import org.fininfo.bazarv3.Bazarv3App;
import org.fininfo.bazarv3.domain.ProduitUnite;
import org.fininfo.bazarv3.repository.ProduitUniteRepository;
import org.fininfo.bazarv3.repository.search.ProduitUniteSearchRepository;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProduitUniteResource} REST controller.
 */
@SpringBootTest(classes = Bazarv3App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProduitUniteResourceIT {

    private static final String DEFAULT_PRDUNITE = "AAAAAAAAAA";
    private static final String UPDATED_PRDUNITE = "BBBBBBBBBB";

    @Autowired
    private ProduitUniteRepository produitUniteRepository;

    /**
     * This repository is mocked in the org.fininfo.bazarv3.repository.search test package.
     *
     * @see org.fininfo.bazarv3.repository.search.ProduitUniteSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProduitUniteSearchRepository mockProduitUniteSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProduitUniteMockMvc;

    private ProduitUnite produitUnite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProduitUnite createEntity(EntityManager em) {
        ProduitUnite produitUnite = new ProduitUnite()
            .prdunite(DEFAULT_PRDUNITE);
        return produitUnite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProduitUnite createUpdatedEntity(EntityManager em) {
        ProduitUnite produitUnite = new ProduitUnite()
            .prdunite(UPDATED_PRDUNITE);
        return produitUnite;
    }

    @BeforeEach
    public void initTest() {
        produitUnite = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduitUnite() throws Exception {
        int databaseSizeBeforeCreate = produitUniteRepository.findAll().size();

        // Create the ProduitUnite
        restProduitUniteMockMvc.perform(post("/api/produit-unites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitUnite)))
            .andExpect(status().isCreated());

        // Validate the ProduitUnite in the database
        List<ProduitUnite> produitUniteList = produitUniteRepository.findAll();
        assertThat(produitUniteList).hasSize(databaseSizeBeforeCreate + 1);
        ProduitUnite testProduitUnite = produitUniteList.get(produitUniteList.size() - 1);
        assertThat(testProduitUnite.getPrdunite()).isEqualTo(DEFAULT_PRDUNITE);

        // Validate the ProduitUnite in Elasticsearch
        verify(mockProduitUniteSearchRepository, times(1)).save(testProduitUnite);
    }

    @Test
    @Transactional
    public void createProduitUniteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produitUniteRepository.findAll().size();

        // Create the ProduitUnite with an existing ID
        produitUnite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduitUniteMockMvc.perform(post("/api/produit-unites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitUnite)))
            .andExpect(status().isBadRequest());

        // Validate the ProduitUnite in the database
        List<ProduitUnite> produitUniteList = produitUniteRepository.findAll();
        assertThat(produitUniteList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProduitUnite in Elasticsearch
        verify(mockProduitUniteSearchRepository, times(0)).save(produitUnite);
    }


    @Test
    @Transactional
    public void getAllProduitUnites() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        // Get all the produitUniteList
        restProduitUniteMockMvc.perform(get("/api/produit-unites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produitUnite.getId().intValue())))
            .andExpect(jsonPath("$.[*].prdunite").value(hasItem(DEFAULT_PRDUNITE)));
    }
    
    @Test
    @Transactional
    public void getProduitUnite() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        // Get the produitUnite
        restProduitUniteMockMvc.perform(get("/api/produit-unites/{id}", produitUnite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(produitUnite.getId().intValue()))
            .andExpect(jsonPath("$.prdunite").value(DEFAULT_PRDUNITE));
    }

    @Test
    @Transactional
    public void getNonExistingProduitUnite() throws Exception {
        // Get the produitUnite
        restProduitUniteMockMvc.perform(get("/api/produit-unites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduitUnite() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        int databaseSizeBeforeUpdate = produitUniteRepository.findAll().size();

        // Update the produitUnite
        ProduitUnite updatedProduitUnite = produitUniteRepository.findById(produitUnite.getId()).get();
        // Disconnect from session so that the updates on updatedProduitUnite are not directly saved in db
        em.detach(updatedProduitUnite);
        updatedProduitUnite
            .prdunite(UPDATED_PRDUNITE);

        restProduitUniteMockMvc.perform(put("/api/produit-unites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduitUnite)))
            .andExpect(status().isOk());

        // Validate the ProduitUnite in the database
        List<ProduitUnite> produitUniteList = produitUniteRepository.findAll();
        assertThat(produitUniteList).hasSize(databaseSizeBeforeUpdate);
        ProduitUnite testProduitUnite = produitUniteList.get(produitUniteList.size() - 1);
        assertThat(testProduitUnite.getPrdunite()).isEqualTo(UPDATED_PRDUNITE);

        // Validate the ProduitUnite in Elasticsearch
        verify(mockProduitUniteSearchRepository, times(1)).save(testProduitUnite);
    }

    @Test
    @Transactional
    public void updateNonExistingProduitUnite() throws Exception {
        int databaseSizeBeforeUpdate = produitUniteRepository.findAll().size();

        // Create the ProduitUnite

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitUniteMockMvc.perform(put("/api/produit-unites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produitUnite)))
            .andExpect(status().isBadRequest());

        // Validate the ProduitUnite in the database
        List<ProduitUnite> produitUniteList = produitUniteRepository.findAll();
        assertThat(produitUniteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProduitUnite in Elasticsearch
        verify(mockProduitUniteSearchRepository, times(0)).save(produitUnite);
    }

    @Test
    @Transactional
    public void deleteProduitUnite() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);

        int databaseSizeBeforeDelete = produitUniteRepository.findAll().size();

        // Delete the produitUnite
        restProduitUniteMockMvc.perform(delete("/api/produit-unites/{id}", produitUnite.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProduitUnite> produitUniteList = produitUniteRepository.findAll();
        assertThat(produitUniteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProduitUnite in Elasticsearch
        verify(mockProduitUniteSearchRepository, times(1)).deleteById(produitUnite.getId());
    }

    @Test
    @Transactional
    public void searchProduitUnite() throws Exception {
        // Initialize the database
        produitUniteRepository.saveAndFlush(produitUnite);
        when(mockProduitUniteSearchRepository.search(queryStringQuery("id:" + produitUnite.getId())))
            .thenReturn(Collections.singletonList(produitUnite));
        // Search the produitUnite
        restProduitUniteMockMvc.perform(get("/api/_search/produit-unites?query=id:" + produitUnite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produitUnite.getId().intValue())))
            .andExpect(jsonPath("$.[*].prdunite").value(hasItem(DEFAULT_PRDUNITE)));
    }
}
