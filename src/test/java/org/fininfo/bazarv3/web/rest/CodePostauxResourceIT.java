package org.fininfo.bazarv3.web.rest;

import org.fininfo.bazarv3.Bazarv3App;
import org.fininfo.bazarv3.domain.CodePostaux;
import org.fininfo.bazarv3.repository.CodePostauxRepository;
import org.fininfo.bazarv3.repository.search.CodePostauxSearchRepository;
import org.fininfo.bazarv3.service.CodePostauxService;
import org.fininfo.bazarv3.service.dto.CodePostauxDTO;
import org.fininfo.bazarv3.service.mapper.CodePostauxMapper;

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
 * Integration tests for the {@link CodePostauxResource} REST controller.
 */
@SpringBootTest(classes = Bazarv3App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CodePostauxResourceIT {

    private static final String DEFAULT_GOUVERNORAT = "AAAAAAAAAA";
    private static final String UPDATED_GOUVERNORAT = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALITE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALITE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODE_POSTAL = 1;
    private static final Integer UPDATED_CODE_POSTAL = 2;

    private static final LocalDate DEFAULT_MODIFIE_LE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIE_LE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_MODIFIE_PAR = 1;
    private static final Integer UPDATED_MODIFIE_PAR = 2;

    @Autowired
    private CodePostauxRepository codePostauxRepository;

    @Autowired
    private CodePostauxMapper codePostauxMapper;

    @Autowired
    private CodePostauxService codePostauxService;

    /**
     * This repository is mocked in the org.fininfo.bazarv3.repository.search test package.
     *
     * @see org.fininfo.bazarv3.repository.search.CodePostauxSearchRepositoryMockConfiguration
     */
    @Autowired
    private CodePostauxSearchRepository mockCodePostauxSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCodePostauxMockMvc;

    private CodePostaux codePostaux;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodePostaux createEntity(EntityManager em) {
        CodePostaux codePostaux = new CodePostaux()
            .gouvernorat(DEFAULT_GOUVERNORAT)
            .ville(DEFAULT_VILLE)
            .localite(DEFAULT_LOCALITE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR);
        return codePostaux;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodePostaux createUpdatedEntity(EntityManager em) {
        CodePostaux codePostaux = new CodePostaux()
            .gouvernorat(UPDATED_GOUVERNORAT)
            .ville(UPDATED_VILLE)
            .localite(UPDATED_LOCALITE)
            .codePostal(UPDATED_CODE_POSTAL)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        return codePostaux;
    }

    @BeforeEach
    public void initTest() {
        codePostaux = createEntity(em);
    }

    @Test
    @Transactional
    public void createCodePostaux() throws Exception {
        int databaseSizeBeforeCreate = codePostauxRepository.findAll().size();

        // Create the CodePostaux
        CodePostauxDTO codePostauxDTO = codePostauxMapper.toDto(codePostaux);
        restCodePostauxMockMvc.perform(post("/api/code-postauxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codePostauxDTO)))
            .andExpect(status().isCreated());

        // Validate the CodePostaux in the database
        List<CodePostaux> codePostauxList = codePostauxRepository.findAll();
        assertThat(codePostauxList).hasSize(databaseSizeBeforeCreate + 1);
        CodePostaux testCodePostaux = codePostauxList.get(codePostauxList.size() - 1);
        assertThat(testCodePostaux.getGouvernorat()).isEqualTo(DEFAULT_GOUVERNORAT);
        assertThat(testCodePostaux.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testCodePostaux.getLocalite()).isEqualTo(DEFAULT_LOCALITE);
        assertThat(testCodePostaux.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testCodePostaux.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testCodePostaux.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);

        // Validate the CodePostaux in Elasticsearch
        verify(mockCodePostauxSearchRepository, times(1)).save(testCodePostaux);
    }

    @Test
    @Transactional
    public void createCodePostauxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = codePostauxRepository.findAll().size();

        // Create the CodePostaux with an existing ID
        codePostaux.setId(1L);
        CodePostauxDTO codePostauxDTO = codePostauxMapper.toDto(codePostaux);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodePostauxMockMvc.perform(post("/api/code-postauxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codePostauxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CodePostaux in the database
        List<CodePostaux> codePostauxList = codePostauxRepository.findAll();
        assertThat(codePostauxList).hasSize(databaseSizeBeforeCreate);

        // Validate the CodePostaux in Elasticsearch
        verify(mockCodePostauxSearchRepository, times(0)).save(codePostaux);
    }


    @Test
    @Transactional
    public void getAllCodePostauxes() throws Exception {
        // Initialize the database
        codePostauxRepository.saveAndFlush(codePostaux);

        // Get all the codePostauxList
        restCodePostauxMockMvc.perform(get("/api/code-postauxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codePostaux.getId().intValue())))
            .andExpect(jsonPath("$.[*].gouvernorat").value(hasItem(DEFAULT_GOUVERNORAT)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].localite").value(hasItem(DEFAULT_LOCALITE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
    
    @Test
    @Transactional
    public void getCodePostaux() throws Exception {
        // Initialize the database
        codePostauxRepository.saveAndFlush(codePostaux);

        // Get the codePostaux
        restCodePostauxMockMvc.perform(get("/api/code-postauxes/{id}", codePostaux.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(codePostaux.getId().intValue()))
            .andExpect(jsonPath("$.gouvernorat").value(DEFAULT_GOUVERNORAT))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.localite").value(DEFAULT_LOCALITE))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR));
    }

    @Test
    @Transactional
    public void getNonExistingCodePostaux() throws Exception {
        // Get the codePostaux
        restCodePostauxMockMvc.perform(get("/api/code-postauxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCodePostaux() throws Exception {
        // Initialize the database
        codePostauxRepository.saveAndFlush(codePostaux);

        int databaseSizeBeforeUpdate = codePostauxRepository.findAll().size();

        // Update the codePostaux
        CodePostaux updatedCodePostaux = codePostauxRepository.findById(codePostaux.getId()).get();
        // Disconnect from session so that the updates on updatedCodePostaux are not directly saved in db
        em.detach(updatedCodePostaux);
        updatedCodePostaux
            .gouvernorat(UPDATED_GOUVERNORAT)
            .ville(UPDATED_VILLE)
            .localite(UPDATED_LOCALITE)
            .codePostal(UPDATED_CODE_POSTAL)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        CodePostauxDTO codePostauxDTO = codePostauxMapper.toDto(updatedCodePostaux);

        restCodePostauxMockMvc.perform(put("/api/code-postauxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codePostauxDTO)))
            .andExpect(status().isOk());

        // Validate the CodePostaux in the database
        List<CodePostaux> codePostauxList = codePostauxRepository.findAll();
        assertThat(codePostauxList).hasSize(databaseSizeBeforeUpdate);
        CodePostaux testCodePostaux = codePostauxList.get(codePostauxList.size() - 1);
        assertThat(testCodePostaux.getGouvernorat()).isEqualTo(UPDATED_GOUVERNORAT);
        assertThat(testCodePostaux.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testCodePostaux.getLocalite()).isEqualTo(UPDATED_LOCALITE);
        assertThat(testCodePostaux.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testCodePostaux.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testCodePostaux.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);

        // Validate the CodePostaux in Elasticsearch
        verify(mockCodePostauxSearchRepository, times(1)).save(testCodePostaux);
    }

    @Test
    @Transactional
    public void updateNonExistingCodePostaux() throws Exception {
        int databaseSizeBeforeUpdate = codePostauxRepository.findAll().size();

        // Create the CodePostaux
        CodePostauxDTO codePostauxDTO = codePostauxMapper.toDto(codePostaux);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodePostauxMockMvc.perform(put("/api/code-postauxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codePostauxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CodePostaux in the database
        List<CodePostaux> codePostauxList = codePostauxRepository.findAll();
        assertThat(codePostauxList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CodePostaux in Elasticsearch
        verify(mockCodePostauxSearchRepository, times(0)).save(codePostaux);
    }

    @Test
    @Transactional
    public void deleteCodePostaux() throws Exception {
        // Initialize the database
        codePostauxRepository.saveAndFlush(codePostaux);

        int databaseSizeBeforeDelete = codePostauxRepository.findAll().size();

        // Delete the codePostaux
        restCodePostauxMockMvc.perform(delete("/api/code-postauxes/{id}", codePostaux.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CodePostaux> codePostauxList = codePostauxRepository.findAll();
        assertThat(codePostauxList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CodePostaux in Elasticsearch
        verify(mockCodePostauxSearchRepository, times(1)).deleteById(codePostaux.getId());
    }

    @Test
    @Transactional
    public void searchCodePostaux() throws Exception {
        // Initialize the database
        codePostauxRepository.saveAndFlush(codePostaux);
        when(mockCodePostauxSearchRepository.search(queryStringQuery("id:" + codePostaux.getId())))
            .thenReturn(Collections.singletonList(codePostaux));
        // Search the codePostaux
        restCodePostauxMockMvc.perform(get("/api/_search/code-postauxes?query=id:" + codePostaux.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codePostaux.getId().intValue())))
            .andExpect(jsonPath("$.[*].gouvernorat").value(hasItem(DEFAULT_GOUVERNORAT)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].localite").value(hasItem(DEFAULT_LOCALITE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
}
