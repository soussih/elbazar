package org.fininfo.bazarv3.web.rest;

import org.fininfo.bazarv3.Bazarv3App;
import org.fininfo.bazarv3.domain.CommandeLignes;
import org.fininfo.bazarv3.repository.CommandeLignesRepository;
import org.fininfo.bazarv3.repository.search.CommandeLignesSearchRepository;
import org.fininfo.bazarv3.service.CommandeLignesService;
import org.fininfo.bazarv3.service.dto.CommandeLignesDTO;
import org.fininfo.bazarv3.service.mapper.CommandeLignesMapper;

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
 * Integration tests for the {@link CommandeLignesResource} REST controller.
 */
@SpringBootTest(classes = Bazarv3App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommandeLignesResourceIT {

    private static final Double DEFAULT_QUANTITE = 1D;
    private static final Double UPDATED_QUANTITE = 2D;

    private static final Double DEFAULT_PRIX_HT = 1D;
    private static final Double UPDATED_PRIX_HT = 2D;

    private static final Double DEFAULT_TVA = 1D;
    private static final Double UPDATED_TVA = 2D;

    private static final Double DEFAULT_PRIX_TTC = 1D;
    private static final Double UPDATED_PRIX_TTC = 2D;

    private static final LocalDate DEFAULT_CREE_LE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREE_LE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREE_PAR = 1;
    private static final Integer UPDATED_CREE_PAR = 2;

    private static final LocalDate DEFAULT_MODIFIE_LE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIE_LE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_MODIFIE_PAR = 1;
    private static final Integer UPDATED_MODIFIE_PAR = 2;

    @Autowired
    private CommandeLignesRepository commandeLignesRepository;

    @Autowired
    private CommandeLignesMapper commandeLignesMapper;

    @Autowired
    private CommandeLignesService commandeLignesService;

    /**
     * This repository is mocked in the org.fininfo.bazarv3.repository.search test package.
     *
     * @see org.fininfo.bazarv3.repository.search.CommandeLignesSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommandeLignesSearchRepository mockCommandeLignesSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommandeLignesMockMvc;

    private CommandeLignes commandeLignes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeLignes createEntity(EntityManager em) {
        CommandeLignes commandeLignes = new CommandeLignes()
            .quantite(DEFAULT_QUANTITE)
            .prixHT(DEFAULT_PRIX_HT)
            .tva(DEFAULT_TVA)
            .prixTTC(DEFAULT_PRIX_TTC)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR);
        return commandeLignes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommandeLignes createUpdatedEntity(EntityManager em) {
        CommandeLignes commandeLignes = new CommandeLignes()
            .quantite(UPDATED_QUANTITE)
            .prixHT(UPDATED_PRIX_HT)
            .tva(UPDATED_TVA)
            .prixTTC(UPDATED_PRIX_TTC)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        return commandeLignes;
    }

    @BeforeEach
    public void initTest() {
        commandeLignes = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommandeLignes() throws Exception {
        int databaseSizeBeforeCreate = commandeLignesRepository.findAll().size();

        // Create the CommandeLignes
        CommandeLignesDTO commandeLignesDTO = commandeLignesMapper.toDto(commandeLignes);
        restCommandeLignesMockMvc.perform(post("/api/commande-lignes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLignesDTO)))
            .andExpect(status().isCreated());

        // Validate the CommandeLignes in the database
        List<CommandeLignes> commandeLignesList = commandeLignesRepository.findAll();
        assertThat(commandeLignesList).hasSize(databaseSizeBeforeCreate + 1);
        CommandeLignes testCommandeLignes = commandeLignesList.get(commandeLignesList.size() - 1);
        assertThat(testCommandeLignes.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testCommandeLignes.getPrixHT()).isEqualTo(DEFAULT_PRIX_HT);
        assertThat(testCommandeLignes.getTva()).isEqualTo(DEFAULT_TVA);
        assertThat(testCommandeLignes.getPrixTTC()).isEqualTo(DEFAULT_PRIX_TTC);
        assertThat(testCommandeLignes.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testCommandeLignes.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testCommandeLignes.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testCommandeLignes.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);

        // Validate the CommandeLignes in Elasticsearch
        verify(mockCommandeLignesSearchRepository, times(1)).save(testCommandeLignes);
    }

    @Test
    @Transactional
    public void createCommandeLignesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandeLignesRepository.findAll().size();

        // Create the CommandeLignes with an existing ID
        commandeLignes.setId(1L);
        CommandeLignesDTO commandeLignesDTO = commandeLignesMapper.toDto(commandeLignes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandeLignesMockMvc.perform(post("/api/commande-lignes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLignesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommandeLignes in the database
        List<CommandeLignes> commandeLignesList = commandeLignesRepository.findAll();
        assertThat(commandeLignesList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommandeLignes in Elasticsearch
        verify(mockCommandeLignesSearchRepository, times(0)).save(commandeLignes);
    }


    @Test
    @Transactional
    public void getAllCommandeLignes() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get all the commandeLignesList
        restCommandeLignesMockMvc.perform(get("/api/commande-lignes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandeLignes.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())))
            .andExpect(jsonPath("$.[*].prixHT").value(hasItem(DEFAULT_PRIX_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].tva").value(hasItem(DEFAULT_TVA.doubleValue())))
            .andExpect(jsonPath("$.[*].prixTTC").value(hasItem(DEFAULT_PRIX_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
    
    @Test
    @Transactional
    public void getCommandeLignes() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        // Get the commandeLignes
        restCommandeLignesMockMvc.perform(get("/api/commande-lignes/{id}", commandeLignes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commandeLignes.getId().intValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.doubleValue()))
            .andExpect(jsonPath("$.prixHT").value(DEFAULT_PRIX_HT.doubleValue()))
            .andExpect(jsonPath("$.tva").value(DEFAULT_TVA.doubleValue()))
            .andExpect(jsonPath("$.prixTTC").value(DEFAULT_PRIX_TTC.doubleValue()))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR));
    }

    @Test
    @Transactional
    public void getNonExistingCommandeLignes() throws Exception {
        // Get the commandeLignes
        restCommandeLignesMockMvc.perform(get("/api/commande-lignes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommandeLignes() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        int databaseSizeBeforeUpdate = commandeLignesRepository.findAll().size();

        // Update the commandeLignes
        CommandeLignes updatedCommandeLignes = commandeLignesRepository.findById(commandeLignes.getId()).get();
        // Disconnect from session so that the updates on updatedCommandeLignes are not directly saved in db
        em.detach(updatedCommandeLignes);
        updatedCommandeLignes
            .quantite(UPDATED_QUANTITE)
            .prixHT(UPDATED_PRIX_HT)
            .tva(UPDATED_TVA)
            .prixTTC(UPDATED_PRIX_TTC)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        CommandeLignesDTO commandeLignesDTO = commandeLignesMapper.toDto(updatedCommandeLignes);

        restCommandeLignesMockMvc.perform(put("/api/commande-lignes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLignesDTO)))
            .andExpect(status().isOk());

        // Validate the CommandeLignes in the database
        List<CommandeLignes> commandeLignesList = commandeLignesRepository.findAll();
        assertThat(commandeLignesList).hasSize(databaseSizeBeforeUpdate);
        CommandeLignes testCommandeLignes = commandeLignesList.get(commandeLignesList.size() - 1);
        assertThat(testCommandeLignes.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testCommandeLignes.getPrixHT()).isEqualTo(UPDATED_PRIX_HT);
        assertThat(testCommandeLignes.getTva()).isEqualTo(UPDATED_TVA);
        assertThat(testCommandeLignes.getPrixTTC()).isEqualTo(UPDATED_PRIX_TTC);
        assertThat(testCommandeLignes.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testCommandeLignes.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testCommandeLignes.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testCommandeLignes.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);

        // Validate the CommandeLignes in Elasticsearch
        verify(mockCommandeLignesSearchRepository, times(1)).save(testCommandeLignes);
    }

    @Test
    @Transactional
    public void updateNonExistingCommandeLignes() throws Exception {
        int databaseSizeBeforeUpdate = commandeLignesRepository.findAll().size();

        // Create the CommandeLignes
        CommandeLignesDTO commandeLignesDTO = commandeLignesMapper.toDto(commandeLignes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandeLignesMockMvc.perform(put("/api/commande-lignes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandeLignesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommandeLignes in the database
        List<CommandeLignes> commandeLignesList = commandeLignesRepository.findAll();
        assertThat(commandeLignesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommandeLignes in Elasticsearch
        verify(mockCommandeLignesSearchRepository, times(0)).save(commandeLignes);
    }

    @Test
    @Transactional
    public void deleteCommandeLignes() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);

        int databaseSizeBeforeDelete = commandeLignesRepository.findAll().size();

        // Delete the commandeLignes
        restCommandeLignesMockMvc.perform(delete("/api/commande-lignes/{id}", commandeLignes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommandeLignes> commandeLignesList = commandeLignesRepository.findAll();
        assertThat(commandeLignesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommandeLignes in Elasticsearch
        verify(mockCommandeLignesSearchRepository, times(1)).deleteById(commandeLignes.getId());
    }

    @Test
    @Transactional
    public void searchCommandeLignes() throws Exception {
        // Initialize the database
        commandeLignesRepository.saveAndFlush(commandeLignes);
        when(mockCommandeLignesSearchRepository.search(queryStringQuery("id:" + commandeLignes.getId())))
            .thenReturn(Collections.singletonList(commandeLignes));
        // Search the commandeLignes
        restCommandeLignesMockMvc.perform(get("/api/_search/commande-lignes?query=id:" + commandeLignes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandeLignes.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.doubleValue())))
            .andExpect(jsonPath("$.[*].prixHT").value(hasItem(DEFAULT_PRIX_HT.doubleValue())))
            .andExpect(jsonPath("$.[*].tva").value(hasItem(DEFAULT_TVA.doubleValue())))
            .andExpect(jsonPath("$.[*].prixTTC").value(hasItem(DEFAULT_PRIX_TTC.doubleValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
}
