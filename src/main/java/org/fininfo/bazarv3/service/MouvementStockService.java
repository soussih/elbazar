package org.fininfo.bazarv3.service;

import org.fininfo.bazarv3.domain.MouvementStock;
import org.fininfo.bazarv3.repository.MouvementStockRepository;
import org.fininfo.bazarv3.repository.search.MouvementStockSearchRepository;
import org.fininfo.bazarv3.service.dto.MouvementStockDTO;
import org.fininfo.bazarv3.service.mapper.MouvementStockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MouvementStock}.
 */
@Service
@Transactional
public class MouvementStockService {

    private final Logger log = LoggerFactory.getLogger(MouvementStockService.class);

    private final MouvementStockRepository mouvementStockRepository;

    private final MouvementStockMapper mouvementStockMapper;

    private final MouvementStockSearchRepository mouvementStockSearchRepository;

    public MouvementStockService(MouvementStockRepository mouvementStockRepository, MouvementStockMapper mouvementStockMapper, MouvementStockSearchRepository mouvementStockSearchRepository) {
        this.mouvementStockRepository = mouvementStockRepository;
        this.mouvementStockMapper = mouvementStockMapper;
        this.mouvementStockSearchRepository = mouvementStockSearchRepository;
    }

    /**
     * Save a mouvementStock.
     *
     * @param mouvementStockDTO the entity to save.
     * @return the persisted entity.
     */
    public MouvementStockDTO save(MouvementStockDTO mouvementStockDTO) {
        log.debug("Request to save MouvementStock : {}", mouvementStockDTO);
        MouvementStock mouvementStock = mouvementStockMapper.toEntity(mouvementStockDTO);
        mouvementStock = mouvementStockRepository.save(mouvementStock);
        MouvementStockDTO result = mouvementStockMapper.toDto(mouvementStock);
        mouvementStockSearchRepository.save(mouvementStock);
        return result;
    }

    /**
     * Get all the mouvementStocks.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MouvementStockDTO> findAll() {
        log.debug("Request to get all MouvementStocks");
        return mouvementStockRepository.findAll().stream()
            .map(mouvementStockMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one mouvementStock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MouvementStockDTO> findOne(Long id) {
        log.debug("Request to get MouvementStock : {}", id);
        return mouvementStockRepository.findById(id)
            .map(mouvementStockMapper::toDto);
    }

    /**
     * Delete the mouvementStock by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MouvementStock : {}", id);
        mouvementStockRepository.deleteById(id);
        mouvementStockSearchRepository.deleteById(id);
    }

    /**
     * Search for the mouvementStock corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MouvementStockDTO> search(String query) {
        log.debug("Request to search MouvementStocks for query {}", query);
        return StreamSupport
            .stream(mouvementStockSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(mouvementStockMapper::toDto)
            .collect(Collectors.toList());
    }
}
