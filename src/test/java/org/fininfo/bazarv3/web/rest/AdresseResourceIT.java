package org.fininfo.bazarv3.web.rest;

import org.fininfo.bazarv3.Bazarv3App;
import org.fininfo.bazarv3.domain.Adresse;
import org.fininfo.bazarv3.repository.AdresseRepository;
import org.fininfo.bazarv3.repository.search.AdresseSearchRepository;
import org.fininfo.bazarv3.service.AdresseService;
import org.fininfo.bazarv3.service.dto.AdresseDTO;
import org.fininfo.bazarv3.service.mapper.AdresseMapper;

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
 * Integration tests for the {@link AdresseResource} REST controller.
 */
@SpringBootTest(classes = Bazarv3App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AdresseResourceIT {

    private static final Boolean DEFAULT_PRINCIPALE = false;
    private static final Boolean UPDATED_PRINCIPALE = true;

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_ADRESSE_LIGNE_1 = 1;
    private static final Integer UPDATED_ADRESSE_LIGNE_1 = 2;

    private static final Integer DEFAULT_ADRESSE_LIGNE_2 = 1;
    private static final Integer UPDATED_ADRESSE_LIGNE_2 = 2;

    private static final String DEFAULT_GOUVERNORAT = "AAAAAAAAAA";
    private static final String UPDATED_GOUVERNORAT = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_CITE = "AAAAAAAAAA";
    private static final String UPDATED_CITE = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATION = "AAAAAAAAAA";
    private static final String UPDATED_INDICATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEPHONE = 1;
    private static final Integer UPDATED_TELEPHONE = 2;

    private static final Integer DEFAULT_MOBILE = 1;
    private static final Integer UPDATED_MOBILE = 2;

    private static final LocalDate DEFAULT_CREE_LE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREE_LE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREE_PAR = 1;
    private static final Integer UPDATED_CREE_PAR = 2;

    private static final LocalDate DEFAULT_MODIFIE_LE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIE_LE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_MODIFIE_PAR = 1;
    private static final Integer UPDATED_MODIFIE_PAR = 2;

    @Autowired
    private AdresseRepository adresseRepository;

    @Autowired
    private AdresseMapper adresseMapper;

    @Autowired
    private AdresseService adresseService;

    /**
     * This repository is mocked in the org.fininfo.bazarv3.repository.search test package.
     *
     * @see org.fininfo.bazarv3.repository.search.AdresseSearchRepositoryMockConfiguration
     */
    @Autowired
    private AdresseSearchRepository mockAdresseSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdresseMockMvc;

    private Adresse adresse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresse createEntity(EntityManager em) {
        Adresse adresse = new Adresse()
            .principale(DEFAULT_PRINCIPALE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .adresseLigne1(DEFAULT_ADRESSE_LIGNE_1)
            .adresseLigne2(DEFAULT_ADRESSE_LIGNE_2)
            .gouvernorat(DEFAULT_GOUVERNORAT)
            .ville(DEFAULT_VILLE)
            .cite(DEFAULT_CITE)
            .indication(DEFAULT_INDICATION)
            .telephone(DEFAULT_TELEPHONE)
            .mobile(DEFAULT_MOBILE)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR);
        return adresse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adresse createUpdatedEntity(EntityManager em) {
        Adresse adresse = new Adresse()
            .principale(UPDATED_PRINCIPALE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .adresseLigne1(UPDATED_ADRESSE_LIGNE_1)
            .adresseLigne2(UPDATED_ADRESSE_LIGNE_2)
            .gouvernorat(UPDATED_GOUVERNORAT)
            .ville(UPDATED_VILLE)
            .cite(UPDATED_CITE)
            .indication(UPDATED_INDICATION)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        return adresse;
    }

    @BeforeEach
    public void initTest() {
        adresse = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdresse() throws Exception {
        int databaseSizeBeforeCreate = adresseRepository.findAll().size();

        // Create the Adresse
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);
        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isCreated());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeCreate + 1);
        Adresse testAdresse = adresseList.get(adresseList.size() - 1);
        assertThat(testAdresse.isPrincipale()).isEqualTo(DEFAULT_PRINCIPALE);
        assertThat(testAdresse.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAdresse.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testAdresse.getAdresseLigne1()).isEqualTo(DEFAULT_ADRESSE_LIGNE_1);
        assertThat(testAdresse.getAdresseLigne2()).isEqualTo(DEFAULT_ADRESSE_LIGNE_2);
        assertThat(testAdresse.getGouvernorat()).isEqualTo(DEFAULT_GOUVERNORAT);
        assertThat(testAdresse.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testAdresse.getCite()).isEqualTo(DEFAULT_CITE);
        assertThat(testAdresse.getIndication()).isEqualTo(DEFAULT_INDICATION);
        assertThat(testAdresse.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testAdresse.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testAdresse.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testAdresse.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testAdresse.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testAdresse.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);

        // Validate the Adresse in Elasticsearch
        verify(mockAdresseSearchRepository, times(1)).save(testAdresse);
    }

    @Test
    @Transactional
    public void createAdresseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adresseRepository.findAll().size();

        // Create the Adresse with an existing ID
        adresse.setId(1L);
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeCreate);

        // Validate the Adresse in Elasticsearch
        verify(mockAdresseSearchRepository, times(0)).save(adresse);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setNom(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setPrenom(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseLigne1IsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setAdresseLigne1(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGouvernoratIsRequired() throws Exception {
        int databaseSizeBeforeTest = adresseRepository.findAll().size();
        // set the field null
        adresse.setGouvernorat(null);

        // Create the Adresse, which fails.
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        restAdresseMockMvc.perform(post("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdresses() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get all the adresseList
        restAdresseMockMvc.perform(get("/api/adresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adresse.getId().intValue())))
            .andExpect(jsonPath("$.[*].principale").value(hasItem(DEFAULT_PRINCIPALE.booleanValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].adresseLigne1").value(hasItem(DEFAULT_ADRESSE_LIGNE_1)))
            .andExpect(jsonPath("$.[*].adresseLigne2").value(hasItem(DEFAULT_ADRESSE_LIGNE_2)))
            .andExpect(jsonPath("$.[*].gouvernorat").value(hasItem(DEFAULT_GOUVERNORAT)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].cite").value(hasItem(DEFAULT_CITE)))
            .andExpect(jsonPath("$.[*].indication").value(hasItem(DEFAULT_INDICATION)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
    
    @Test
    @Transactional
    public void getAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        // Get the adresse
        restAdresseMockMvc.perform(get("/api/adresses/{id}", adresse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adresse.getId().intValue()))
            .andExpect(jsonPath("$.principale").value(DEFAULT_PRINCIPALE.booleanValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.adresseLigne1").value(DEFAULT_ADRESSE_LIGNE_1))
            .andExpect(jsonPath("$.adresseLigne2").value(DEFAULT_ADRESSE_LIGNE_2))
            .andExpect(jsonPath("$.gouvernorat").value(DEFAULT_GOUVERNORAT))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.cite").value(DEFAULT_CITE))
            .andExpect(jsonPath("$.indication").value(DEFAULT_INDICATION))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR));
    }

    @Test
    @Transactional
    public void getNonExistingAdresse() throws Exception {
        // Get the adresse
        restAdresseMockMvc.perform(get("/api/adresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();

        // Update the adresse
        Adresse updatedAdresse = adresseRepository.findById(adresse.getId()).get();
        // Disconnect from session so that the updates on updatedAdresse are not directly saved in db
        em.detach(updatedAdresse);
        updatedAdresse
            .principale(UPDATED_PRINCIPALE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .adresseLigne1(UPDATED_ADRESSE_LIGNE_1)
            .adresseLigne2(UPDATED_ADRESSE_LIGNE_2)
            .gouvernorat(UPDATED_GOUVERNORAT)
            .ville(UPDATED_VILLE)
            .cite(UPDATED_CITE)
            .indication(UPDATED_INDICATION)
            .telephone(UPDATED_TELEPHONE)
            .mobile(UPDATED_MOBILE)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        AdresseDTO adresseDTO = adresseMapper.toDto(updatedAdresse);

        restAdresseMockMvc.perform(put("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isOk());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);
        Adresse testAdresse = adresseList.get(adresseList.size() - 1);
        assertThat(testAdresse.isPrincipale()).isEqualTo(UPDATED_PRINCIPALE);
        assertThat(testAdresse.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAdresse.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testAdresse.getAdresseLigne1()).isEqualTo(UPDATED_ADRESSE_LIGNE_1);
        assertThat(testAdresse.getAdresseLigne2()).isEqualTo(UPDATED_ADRESSE_LIGNE_2);
        assertThat(testAdresse.getGouvernorat()).isEqualTo(UPDATED_GOUVERNORAT);
        assertThat(testAdresse.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testAdresse.getCite()).isEqualTo(UPDATED_CITE);
        assertThat(testAdresse.getIndication()).isEqualTo(UPDATED_INDICATION);
        assertThat(testAdresse.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testAdresse.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testAdresse.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testAdresse.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testAdresse.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testAdresse.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);

        // Validate the Adresse in Elasticsearch
        verify(mockAdresseSearchRepository, times(1)).save(testAdresse);
    }

    @Test
    @Transactional
    public void updateNonExistingAdresse() throws Exception {
        int databaseSizeBeforeUpdate = adresseRepository.findAll().size();

        // Create the Adresse
        AdresseDTO adresseDTO = adresseMapper.toDto(adresse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdresseMockMvc.perform(put("/api/adresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adresseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adresse in the database
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Adresse in Elasticsearch
        verify(mockAdresseSearchRepository, times(0)).save(adresse);
    }

    @Test
    @Transactional
    public void deleteAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);

        int databaseSizeBeforeDelete = adresseRepository.findAll().size();

        // Delete the adresse
        restAdresseMockMvc.perform(delete("/api/adresses/{id}", adresse.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Adresse> adresseList = adresseRepository.findAll();
        assertThat(adresseList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Adresse in Elasticsearch
        verify(mockAdresseSearchRepository, times(1)).deleteById(adresse.getId());
    }

    @Test
    @Transactional
    public void searchAdresse() throws Exception {
        // Initialize the database
        adresseRepository.saveAndFlush(adresse);
        when(mockAdresseSearchRepository.search(queryStringQuery("id:" + adresse.getId())))
            .thenReturn(Collections.singletonList(adresse));
        // Search the adresse
        restAdresseMockMvc.perform(get("/api/_search/adresses?query=id:" + adresse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adresse.getId().intValue())))
            .andExpect(jsonPath("$.[*].principale").value(hasItem(DEFAULT_PRINCIPALE.booleanValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].adresseLigne1").value(hasItem(DEFAULT_ADRESSE_LIGNE_1)))
            .andExpect(jsonPath("$.[*].adresseLigne2").value(hasItem(DEFAULT_ADRESSE_LIGNE_2)))
            .andExpect(jsonPath("$.[*].gouvernorat").value(hasItem(DEFAULT_GOUVERNORAT)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].cite").value(hasItem(DEFAULT_CITE)))
            .andExpect(jsonPath("$.[*].indication").value(hasItem(DEFAULT_INDICATION)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
}
