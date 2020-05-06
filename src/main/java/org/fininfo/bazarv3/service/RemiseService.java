package org.fininfo.bazarv3.service;

import org.fininfo.bazarv3.domain.Remise;
import org.fininfo.bazarv3.repository.RemiseRepository;
import org.fininfo.bazarv3.repository.search.RemiseSearchRepository;
import org.fininfo.bazarv3.service.dto.RemiseDTO;
import org.fininfo.bazarv3.service.mapper.RemiseMapper;
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
 * Service Implementation for managing {@link Remise}.
 */
@Service
@Transactional
public class RemiseService {

    private final Logger log = LoggerFactory.getLogger(RemiseService.class);

    private final RemiseRepository remiseRepository;

    private final RemiseMapper remiseMapper;

    private final RemiseSearchRepository remiseSearchRepository;

    public RemiseService(RemiseRepository remiseRepository, RemiseMapper remiseMapper, RemiseSearchRepository remiseSearchRepository) {
        this.remiseRepository = remiseRepository;
        this.remiseMapper = remiseMapper;
        this.remiseSearchRepository = remiseSearchRepository;
    }

    /**
     * Save a remise.
     *
     * @param remiseDTO the entity to save.
     * @return the persisted entity.
     */
    public RemiseDTO save(RemiseDTO remiseDTO) {
        log.debug("Request to save Remise : {}", remiseDTO);
        Remise remise = remiseMapper.toEntity(remiseDTO);
        remise = remiseRepository.save(remise);
        RemiseDTO result = remiseMapper.toDto(remise);
        remiseSearchRepository.save(remise);
        return result;
    }

    /**
     * Get all the remises.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RemiseDTO> findAll() {
        log.debug("Request to get all Remises");
        return remiseRepository.findAll().stream()
            .map(remiseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one remise by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RemiseDTO> findOne(Long id) {
        log.debug("Request to get Remise : {}", id);
        return remiseRepository.findById(id)
            .map(remiseMapper::toDto);
    }

    /**
     * Delete the remise by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Remise : {}", id);
        remiseRepository.deleteById(id);
        remiseSearchRepository.deleteById(id);
    }

    /**
     * Search for the remise corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RemiseDTO> search(String query) {
        log.debug("Request to search Remises for query {}", query);
        return StreamSupport
            .stream(remiseSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(remiseMapper::toDto)
            .collect(Collectors.toList());
    }
}
