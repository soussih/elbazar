package org.fininfo.bazarv3.web.rest;

import org.fininfo.bazarv3.Bazarv3App;
import org.fininfo.bazarv3.domain.Stock;
import org.fininfo.bazarv3.repository.StockRepository;
import org.fininfo.bazarv3.repository.search.StockSearchRepository;
import org.fininfo.bazarv3.service.StockService;
import org.fininfo.bazarv3.service.dto.StockDTO;
import org.fininfo.bazarv3.service.mapper.StockMapper;

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
 * Integration tests for the {@link StockResource} REST controller.
 */
@SpringBootTest(classes = Bazarv3App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class StockResourceIT {

    private static final Double DEFAULT_STOCK_PHYSIQUE = 1D;
    private static final Double UPDATED_STOCK_PHYSIQUE = 2D;

    private static final Double DEFAULT_STOCK_DISPONIBLE = 1D;
    private static final Double UPDATED_STOCK_DISPONIBLE = 2D;

    private static final Double DEFAULT_STOCK_RESERVE = 1D;
    private static final Double UPDATED_STOCK_RESERVE = 2D;

    private static final Double DEFAULT_STOCK_COMMANDE = 1D;
    private static final Double UPDATED_STOCK_COMMANDE = 2D;

    private static final LocalDate DEFAULT_DERNIERE_ENTRE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DERNIERE_ENTRE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DERNIERE_SORTIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DERNIERE_SORTIE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ALERTE_STOCK = false;
    private static final Boolean UPDATED_ALERTE_STOCK = true;

    private static final LocalDate DEFAULT_CREE_LE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREE_LE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREE_PAR = 1;
    private static final Integer UPDATED_CREE_PAR = 2;

    private static final LocalDate DEFAULT_MODIFIE_LE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIE_LE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_MODIFIE_PAR = 1;
    private static final Integer UPDATED_MODIFIE_PAR = 2;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockService stockService;

    /**
     * This repository is mocked in the org.fininfo.bazarv3.repository.search test package.
     *
     * @see org.fininfo.bazarv3.repository.search.StockSearchRepositoryMockConfiguration
     */
    @Autowired
    private StockSearchRepository mockStockSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockMockMvc;

    private Stock stock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stock createEntity(EntityManager em) {
        Stock stock = new Stock()
            .stockPhysique(DEFAULT_STOCK_PHYSIQUE)
            .stockDisponible(DEFAULT_STOCK_DISPONIBLE)
            .stockReserve(DEFAULT_STOCK_RESERVE)
            .stockCommande(DEFAULT_STOCK_COMMANDE)
            .derniereEntre(DEFAULT_DERNIERE_ENTRE)
            .derniereSortie(DEFAULT_DERNIERE_SORTIE)
            .alerteStock(DEFAULT_ALERTE_STOCK)
            .creeLe(DEFAULT_CREE_LE)
            .creePar(DEFAULT_CREE_PAR)
            .modifieLe(DEFAULT_MODIFIE_LE)
            .modifiePar(DEFAULT_MODIFIE_PAR);
        return stock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stock createUpdatedEntity(EntityManager em) {
        Stock stock = new Stock()
            .stockPhysique(UPDATED_STOCK_PHYSIQUE)
            .stockDisponible(UPDATED_STOCK_DISPONIBLE)
            .stockReserve(UPDATED_STOCK_RESERVE)
            .stockCommande(UPDATED_STOCK_COMMANDE)
            .derniereEntre(UPDATED_DERNIERE_ENTRE)
            .derniereSortie(UPDATED_DERNIERE_SORTIE)
            .alerteStock(UPDATED_ALERTE_STOCK)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        return stock;
    }

    @BeforeEach
    public void initTest() {
        stock = createEntity(em);
    }

    @Test
    @Transactional
    public void createStock() throws Exception {
        int databaseSizeBeforeCreate = stockRepository.findAll().size();

        // Create the Stock
        StockDTO stockDTO = stockMapper.toDto(stock);
        restStockMockMvc.perform(post("/api/stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isCreated());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeCreate + 1);
        Stock testStock = stockList.get(stockList.size() - 1);
        assertThat(testStock.getStockPhysique()).isEqualTo(DEFAULT_STOCK_PHYSIQUE);
        assertThat(testStock.getStockDisponible()).isEqualTo(DEFAULT_STOCK_DISPONIBLE);
        assertThat(testStock.getStockReserve()).isEqualTo(DEFAULT_STOCK_RESERVE);
        assertThat(testStock.getStockCommande()).isEqualTo(DEFAULT_STOCK_COMMANDE);
        assertThat(testStock.getDerniereEntre()).isEqualTo(DEFAULT_DERNIERE_ENTRE);
        assertThat(testStock.getDerniereSortie()).isEqualTo(DEFAULT_DERNIERE_SORTIE);
        assertThat(testStock.isAlerteStock()).isEqualTo(DEFAULT_ALERTE_STOCK);
        assertThat(testStock.getCreeLe()).isEqualTo(DEFAULT_CREE_LE);
        assertThat(testStock.getCreePar()).isEqualTo(DEFAULT_CREE_PAR);
        assertThat(testStock.getModifieLe()).isEqualTo(DEFAULT_MODIFIE_LE);
        assertThat(testStock.getModifiePar()).isEqualTo(DEFAULT_MODIFIE_PAR);

        // Validate the Stock in Elasticsearch
        verify(mockStockSearchRepository, times(1)).save(testStock);
    }

    @Test
    @Transactional
    public void createStockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockRepository.findAll().size();

        // Create the Stock with an existing ID
        stock.setId(1L);
        StockDTO stockDTO = stockMapper.toDto(stock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockMockMvc.perform(post("/api/stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeCreate);

        // Validate the Stock in Elasticsearch
        verify(mockStockSearchRepository, times(0)).save(stock);
    }


    @Test
    @Transactional
    public void getAllStocks() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get all the stockList
        restStockMockMvc.perform(get("/api/stocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stock.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockPhysique").value(hasItem(DEFAULT_STOCK_PHYSIQUE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockDisponible").value(hasItem(DEFAULT_STOCK_DISPONIBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockReserve").value(hasItem(DEFAULT_STOCK_RESERVE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockCommande").value(hasItem(DEFAULT_STOCK_COMMANDE.doubleValue())))
            .andExpect(jsonPath("$.[*].derniereEntre").value(hasItem(DEFAULT_DERNIERE_ENTRE.toString())))
            .andExpect(jsonPath("$.[*].derniereSortie").value(hasItem(DEFAULT_DERNIERE_SORTIE.toString())))
            .andExpect(jsonPath("$.[*].alerteStock").value(hasItem(DEFAULT_ALERTE_STOCK.booleanValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
    
    @Test
    @Transactional
    public void getStock() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        // Get the stock
        restStockMockMvc.perform(get("/api/stocks/{id}", stock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stock.getId().intValue()))
            .andExpect(jsonPath("$.stockPhysique").value(DEFAULT_STOCK_PHYSIQUE.doubleValue()))
            .andExpect(jsonPath("$.stockDisponible").value(DEFAULT_STOCK_DISPONIBLE.doubleValue()))
            .andExpect(jsonPath("$.stockReserve").value(DEFAULT_STOCK_RESERVE.doubleValue()))
            .andExpect(jsonPath("$.stockCommande").value(DEFAULT_STOCK_COMMANDE.doubleValue()))
            .andExpect(jsonPath("$.derniereEntre").value(DEFAULT_DERNIERE_ENTRE.toString()))
            .andExpect(jsonPath("$.derniereSortie").value(DEFAULT_DERNIERE_SORTIE.toString()))
            .andExpect(jsonPath("$.alerteStock").value(DEFAULT_ALERTE_STOCK.booleanValue()))
            .andExpect(jsonPath("$.creeLe").value(DEFAULT_CREE_LE.toString()))
            .andExpect(jsonPath("$.creePar").value(DEFAULT_CREE_PAR))
            .andExpect(jsonPath("$.modifieLe").value(DEFAULT_MODIFIE_LE.toString()))
            .andExpect(jsonPath("$.modifiePar").value(DEFAULT_MODIFIE_PAR));
    }

    @Test
    @Transactional
    public void getNonExistingStock() throws Exception {
        // Get the stock
        restStockMockMvc.perform(get("/api/stocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStock() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        int databaseSizeBeforeUpdate = stockRepository.findAll().size();

        // Update the stock
        Stock updatedStock = stockRepository.findById(stock.getId()).get();
        // Disconnect from session so that the updates on updatedStock are not directly saved in db
        em.detach(updatedStock);
        updatedStock
            .stockPhysique(UPDATED_STOCK_PHYSIQUE)
            .stockDisponible(UPDATED_STOCK_DISPONIBLE)
            .stockReserve(UPDATED_STOCK_RESERVE)
            .stockCommande(UPDATED_STOCK_COMMANDE)
            .derniereEntre(UPDATED_DERNIERE_ENTRE)
            .derniereSortie(UPDATED_DERNIERE_SORTIE)
            .alerteStock(UPDATED_ALERTE_STOCK)
            .creeLe(UPDATED_CREE_LE)
            .creePar(UPDATED_CREE_PAR)
            .modifieLe(UPDATED_MODIFIE_LE)
            .modifiePar(UPDATED_MODIFIE_PAR);
        StockDTO stockDTO = stockMapper.toDto(updatedStock);

        restStockMockMvc.perform(put("/api/stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isOk());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);
        Stock testStock = stockList.get(stockList.size() - 1);
        assertThat(testStock.getStockPhysique()).isEqualTo(UPDATED_STOCK_PHYSIQUE);
        assertThat(testStock.getStockDisponible()).isEqualTo(UPDATED_STOCK_DISPONIBLE);
        assertThat(testStock.getStockReserve()).isEqualTo(UPDATED_STOCK_RESERVE);
        assertThat(testStock.getStockCommande()).isEqualTo(UPDATED_STOCK_COMMANDE);
        assertThat(testStock.getDerniereEntre()).isEqualTo(UPDATED_DERNIERE_ENTRE);
        assertThat(testStock.getDerniereSortie()).isEqualTo(UPDATED_DERNIERE_SORTIE);
        assertThat(testStock.isAlerteStock()).isEqualTo(UPDATED_ALERTE_STOCK);
        assertThat(testStock.getCreeLe()).isEqualTo(UPDATED_CREE_LE);
        assertThat(testStock.getCreePar()).isEqualTo(UPDATED_CREE_PAR);
        assertThat(testStock.getModifieLe()).isEqualTo(UPDATED_MODIFIE_LE);
        assertThat(testStock.getModifiePar()).isEqualTo(UPDATED_MODIFIE_PAR);

        // Validate the Stock in Elasticsearch
        verify(mockStockSearchRepository, times(1)).save(testStock);
    }

    @Test
    @Transactional
    public void updateNonExistingStock() throws Exception {
        int databaseSizeBeforeUpdate = stockRepository.findAll().size();

        // Create the Stock
        StockDTO stockDTO = stockMapper.toDto(stock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockMockMvc.perform(put("/api/stocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Stock in the database
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Stock in Elasticsearch
        verify(mockStockSearchRepository, times(0)).save(stock);
    }

    @Test
    @Transactional
    public void deleteStock() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);

        int databaseSizeBeforeDelete = stockRepository.findAll().size();

        // Delete the stock
        restStockMockMvc.perform(delete("/api/stocks/{id}", stock.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stock> stockList = stockRepository.findAll();
        assertThat(stockList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Stock in Elasticsearch
        verify(mockStockSearchRepository, times(1)).deleteById(stock.getId());
    }

    @Test
    @Transactional
    public void searchStock() throws Exception {
        // Initialize the database
        stockRepository.saveAndFlush(stock);
        when(mockStockSearchRepository.search(queryStringQuery("id:" + stock.getId())))
            .thenReturn(Collections.singletonList(stock));
        // Search the stock
        restStockMockMvc.perform(get("/api/_search/stocks?query=id:" + stock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stock.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockPhysique").value(hasItem(DEFAULT_STOCK_PHYSIQUE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockDisponible").value(hasItem(DEFAULT_STOCK_DISPONIBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockReserve").value(hasItem(DEFAULT_STOCK_RESERVE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockCommande").value(hasItem(DEFAULT_STOCK_COMMANDE.doubleValue())))
            .andExpect(jsonPath("$.[*].derniereEntre").value(hasItem(DEFAULT_DERNIERE_ENTRE.toString())))
            .andExpect(jsonPath("$.[*].derniereSortie").value(hasItem(DEFAULT_DERNIERE_SORTIE.toString())))
            .andExpect(jsonPath("$.[*].alerteStock").value(hasItem(DEFAULT_ALERTE_STOCK.booleanValue())))
            .andExpect(jsonPath("$.[*].creeLe").value(hasItem(DEFAULT_CREE_LE.toString())))
            .andExpect(jsonPath("$.[*].creePar").value(hasItem(DEFAULT_CREE_PAR)))
            .andExpect(jsonPath("$.[*].modifieLe").value(hasItem(DEFAULT_MODIFIE_LE.toString())))
            .andExpect(jsonPath("$.[*].modifiePar").value(hasItem(DEFAULT_MODIFIE_PAR)));
    }
}
