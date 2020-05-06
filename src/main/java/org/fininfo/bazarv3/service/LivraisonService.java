package org.fininfo.bazarv3.service;

import org.fininfo.bazarv3.domain.Livraison;
import org.fininfo.bazarv3.repository.LivraisonRepository;
import org.fininfo.bazarv3.repository.search.LivraisonSearchRepository;
import org.fininfo.bazarv3.service.dto.LivraisonDTO;
import org.fininfo.bazarv3.service.mapper.LivraisonMapper;
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
 * Service Implementation for managing {@link Livraison}.
 */
@Service
@Transactional
public class LivraisonService {

    private final Logger log = LoggerFactory.getLogger(LivraisonService.class);

    private final LivraisonRepository livraisonRepository;

    private final LivraisonMapper livraisonMapper;

    private final LivraisonSearchRepository livraisonSearchRepository;

    public LivraisonService(LivraisonRepository livraisonRepository, LivraisonMapper livraisonMapper, LivraisonSearchRepository livraisonSearchRepository) {
        this.livraisonRepository = livraisonRepository;
        this.livraisonMapper = livraisonMapper;
        this.livraisonSearchRepository = livraisonSearchRepository;
    }

    /**
     * Save a livraison.
     *
     * @param livraisonDTO the entity to save.
     * @return the persisted entity.
     */
    public LivraisonDTO save(LivraisonDTO livraisonDTO) {
        log.debug("Request to save Livraison : {}", livraisonDTO);
        Livraison livraison = livraisonMapper.toEntity(livraisonDTO);
        livraison = livraisonRepository.save(livraison);
        LivraisonDTO result = livraisonMapper.toDto(livraison);
        livraisonSearchRepository.save(livraison);
        return result;
    }

    /**
     * Get all the livraisons.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LivraisonDTO> findAll() {
        log.debug("Request to get all Livraisons");
        return livraisonRepository.findAll().stream()
            .map(livraisonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one livraison by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LivraisonDTO> findOne(Long id) {
        log.debug("Request to get Livraison : {}", id);
        return livraisonRepository.findById(id)
            .map(livraisonMapper::toDto);
    }

    /**
     * Delete the livraison by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Livraison : {}", id);
        livraisonRepository.deleteById(id);
        livraisonSearchRepository.deleteById(id);
    }

    /**
     * Search for the livraison corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LivraisonDTO> search(String query) {
        log.debug("Request to search Livraisons for query {}", query);
        return StreamSupport
            .stream(livraisonSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(livraisonMapper::toDto)
            .collect(Collectors.toList());
    }
}
