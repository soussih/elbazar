package org.fininfo.bazarv3.web.rest;

import org.fininfo.bazarv3.Bazarv3App;
import org.fininfo.bazarv3.domain.ProdEnumeration;
import org.fininfo.bazarv3.repository.ProdEnumerationRepository;
import org.fininfo.bazarv3.repository.search.ProdEnumerationSearchRepository;

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

import org.fininfo.bazarv3.domain.enumeration.ProdEnum;
/**
 * Integration tests for the {@link ProdEnumerationResource} REST controller.
 */
@SpringBootTest(classes = Bazarv3App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProdEnumerationResourceIT {

    private static final ProdEnum DEFAULT_TYPE = ProdEnum.Marque;
    private static final ProdEnum UPDATED_TYPE = ProdEnum.Classe;

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private ProdEnumerationRepository prodEnumerationRepository;

    /**
     * This repository is mocked in the org.fininfo.bazarv3.repository.search test package.
     *
     * @see org.fininfo.bazarv3.repository.search.ProdEnumerationSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProdEnumerationSearchRepository mockProdEnumerationSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProdEnumerationMockMvc;

    private ProdEnumeration prodEnumeration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProdEnumeration createEntity(EntityManager em) {
        ProdEnumeration prodEnumeration = new ProdEnumeration()
            .type(DEFAULT_TYPE)
            .nom(DEFAULT_NOM);
        return prodEnumeration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProdEnumeration createUpdatedEntity(EntityManager em) {
        ProdEnumeration prodEnumeration = new ProdEnumeration()
            .type(UPDATED_TYPE)
            .nom(UPDATED_NOM);
        return prodEnumeration;
    }

    @BeforeEach
    public void initTest() {
        prodEnumeration = createEntity(em);
    }

    @Test
    @Transactional
    public void createProdEnumeration() throws Exception {
        int databaseSizeBeforeCreate = prodEnumerationRepository.findAll().size();

        // Create the ProdEnumeration
        restProdEnumerationMockMvc.perform(post("/api/prod-enumerations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prodEnumeration)))
            .andExpect(status().isCreated());

        // Validate the ProdEnumeration in the database
        List<ProdEnumeration> prodEnumerationList = prodEnumerationRepository.findAll();
        assertThat(prodEnumerationList).hasSize(databaseSizeBeforeCreate + 1);
        ProdEnumeration testProdEnumeration = prodEnumerationList.get(prodEnumerationList.size() - 1);
        assertThat(testProdEnumeration.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProdEnumeration.getNom()).isEqualTo(DEFAULT_NOM);

        // Validate the ProdEnumeration in Elasticsearch
        verify(mockProdEnumerationSearchRepository, times(1)).save(testProdEnumeration);
    }

    @Test
    @Transactional
    public void createProdEnumerationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prodEnumerationRepository.findAll().size();

        // Create the ProdEnumeration with an existing ID
        prodEnumeration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdEnumerationMockMvc.perform(post("/api/prod-enumerations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prodEnumeration)))
            .andExpect(status().isBadRequest());

        // Validate the ProdEnumeration in the database
        List<ProdEnumeration> prodEnumerationList = prodEnumerationRepository.findAll();
        assertThat(prodEnumerationList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProdEnumeration in Elasticsearch
        verify(mockProdEnumerationSearchRepository, times(0)).save(prodEnumeration);
    }


    @Test
    @Transactional
    public void getAllProdEnumerations() throws Exception {
        // Initialize the database
        prodEnumerationRepository.saveAndFlush(prodEnumeration);

        // Get all the prodEnumerationList
        restProdEnumerationMockMvc.perform(get("/api/prod-enumerations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prodEnumeration.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }
    
    @Test
    @Transactional
    public void getProdEnumeration() throws Exception {
        // Initialize the database
        prodEnumerationRepository.saveAndFlush(prodEnumeration);

        // Get the prodEnumeration
        restProdEnumerationMockMvc.perform(get("/api/prod-enumerations/{id}", prodEnumeration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prodEnumeration.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }

    @Test
    @Transactional
    public void getNonExistingProdEnumeration() throws Exception {
        // Get the prodEnumeration
        restProdEnumerationMockMvc.perform(get("/api/prod-enumerations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProdEnumeration() throws Exception {
        // Initialize the database
        prodEnumerationRepository.saveAndFlush(prodEnumeration);

        int databaseSizeBeforeUpdate = prodEnumerationRepository.findAll().size();

        // Update the prodEnumeration
        ProdEnumeration updatedProdEnumeration = prodEnumerationRepository.findById(prodEnumeration.getId()).get();
        // Disconnect from session so that the updates on updatedProdEnumeration are not directly saved in db
        em.detach(updatedProdEnumeration);
        updatedProdEnumeration
            .type(UPDATED_TYPE)
            .nom(UPDATED_NOM);

        restProdEnumerationMockMvc.perform(put("/api/prod-enumerations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProdEnumeration)))
            .andExpect(status().isOk());

        // Validate the ProdEnumeration in the database
        List<ProdEnumeration> prodEnumerationList = prodEnumerationRepository.findAll();
        assertThat(prodEnumerationList).hasSize(databaseSizeBeforeUpdate);
        ProdEnumeration testProdEnumeration = prodEnumerationList.get(prodEnumerationList.size() - 1);
        assertThat(testProdEnumeration.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProdEnumeration.getNom()).isEqualTo(UPDATED_NOM);

        // Validate the ProdEnumeration in Elasticsearch
        verify(mockProdEnumerationSearchRepository, times(1)).save(testProdEnumeration);
    }

    @Test
    @Transactional
    public void updateNonExistingProdEnumeration() throws Exception {
        int databaseSizeBeforeUpdate = prodEnumerationRepository.findAll().size();

        // Create the ProdEnumeration

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdEnumerationMockMvc.perform(put("/api/prod-enumerations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prodEnumeration)))
            .andExpect(status().isBadRequest());

        // Validate the ProdEnumeration in the database
        List<ProdEnumeration> prodEnumerationList = prodEnumerationRepository.findAll();
        assertThat(prodEnumerationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProdEnumeration in Elasticsearch
        verify(mockProdEnumerationSearchRepository, times(0)).save(prodEnumeration);
    }

    @Test
    @Transactional
    public void deleteProdEnumeration() throws Exception {
        // Initialize the database
        prodEnumerationRepository.saveAndFlush(prodEnumeration);

        int databaseSizeBeforeDelete = prodEnumerationRepository.findAll().size();

        // Delete the prodEnumeration
        restProdEnumerationMockMvc.perform(delete("/api/prod-enumerations/{id}", prodEnumeration.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProdEnumeration> prodEnumerationList = prodEnumerationRepository.findAll();
        assertThat(prodEnumerationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProdEnumeration in Elasticsearch
        verify(mockProdEnumerationSearchRepository, times(1)).deleteById(prodEnumeration.getId());
    }

    @Test
    @Transactional
    public void searchProdEnumeration() throws Exception {
        // Initialize the database
        prodEnumerationRepository.saveAndFlush(prodEnumeration);
        when(mockProdEnumerationSearchRepository.search(queryStringQuery("id:" + prodEnumeration.getId())))
            .thenReturn(Collections.singletonList(prodEnumeration));
        // Search the prodEnumeration
        restProdEnumerationMockMvc.perform(get("/api/_search/prod-enumerations?query=id:" + prodEnumeration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prodEnumeration.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }
}
