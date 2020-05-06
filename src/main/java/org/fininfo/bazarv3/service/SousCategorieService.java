package org.fininfo.bazarv3.service;

import org.fininfo.bazarv3.domain.SousCategorie;
import org.fininfo.bazarv3.repository.SousCategorieRepository;
import org.fininfo.bazarv3.repository.search.SousCategorieSearchRepository;
import org.fininfo.bazarv3.service.dto.SousCategorieDTO;
import org.fininfo.bazarv3.service.mapper.SousCategorieMapper;
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
 * Service Implementation for managing {@link SousCategorie}.
 */
@Service
@Transactional
public class SousCategorieService {

    private final Logger log = LoggerFactory.getLogger(SousCategorieService.class);

    private final SousCategorieRepository sousCategorieRepository;

    private final SousCategorieMapper sousCategorieMapper;

    private final SousCategorieSearchRepository sousCategorieSearchRepository;

    public SousCategorieService(SousCategorieRepository sousCategorieRepository, SousCategorieMapper sousCategorieMapper, SousCategorieSearchRepository sousCategorieSearchRepository) {
        this.sousCategorieRepository = sousCategorieRepository;
        this.sousCategorieMapper = sousCategorieMapper;
        this.sousCategorieSearchRepository = sousCategorieSearchRepository;
    }

    /**
     * Save a sousCategorie.
     *
     * @param sousCategorieDTO the entity to save.
     * @return the persisted entity.
     */
    public SousCategorieDTO save(SousCategorieDTO sousCategorieDTO) {
        log.debug("Request to save SousCategorie : {}", sousCategorieDTO);
        SousCategorie sousCategorie = sousCategorieMapper.toEntity(sousCategorieDTO);
        sousCategorie = sousCategorieRepository.save(sousCategorie);
        SousCategorieDTO result = sousCategorieMapper.toDto(sousCategorie);
        sousCategorieSearchRepository.save(sousCategorie);
        return result;
    }

    /**
     * Get all the sousCategories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SousCategorieDTO> findAll() {
        log.debug("Request to get all SousCategories");
        return sousCategorieRepository.findAll().stream()
            .map(sousCategorieMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sousCategorie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SousCategorieDTO> findOne(Long id) {
        log.debug("Request to get SousCategorie : {}", id);
        return sousCategorieRepository.findById(id)
            .map(sousCategorieMapper::toDto);
    }

    /**
     * Delete the sousCategorie by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SousCategorie : {}", id);
        sousCategorieRepository.deleteById(id);
        sousCategorieSearchRepository.deleteById(id);
    }

    /**
     * Search for the sousCategorie corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SousCategorieDTO> search(String query) {
        log.debug("Request to search SousCategories for query {}", query);
        return StreamSupport
            .stream(sousCategorieSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(sousCategorieMapper::toDto)
            .collect(Collectors.toList());
    }
}
