package org.fininfo.bazarv3.service;

import org.fininfo.bazarv3.domain.CodePostaux;
import org.fininfo.bazarv3.repository.CodePostauxRepository;
import org.fininfo.bazarv3.repository.search.CodePostauxSearchRepository;
import org.fininfo.bazarv3.service.dto.CodePostauxDTO;
import org.fininfo.bazarv3.service.mapper.CodePostauxMapper;
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
 * Service Implementation for managing {@link CodePostaux}.
 */
@Service
@Transactional
public class CodePostauxService {

    private final Logger log = LoggerFactory.getLogger(CodePostauxService.class);

    private final CodePostauxRepository codePostauxRepository;

    private final CodePostauxMapper codePostauxMapper;

    private final CodePostauxSearchRepository codePostauxSearchRepository;

    public CodePostauxService(CodePostauxRepository codePostauxRepository, CodePostauxMapper codePostauxMapper, CodePostauxSearchRepository codePostauxSearchRepository) {
        this.codePostauxRepository = codePostauxRepository;
        this.codePostauxMapper = codePostauxMapper;
        this.codePostauxSearchRepository = codePostauxSearchRepository;
    }

    /**
     * Save a codePostaux.
     *
     * @param codePostauxDTO the entity to save.
     * @return the persisted entity.
     */
    public CodePostauxDTO save(CodePostauxDTO codePostauxDTO) {
        log.debug("Request to save CodePostaux : {}", codePostauxDTO);
        CodePostaux codePostaux = codePostauxMapper.toEntity(codePostauxDTO);
        codePostaux = codePostauxRepository.save(codePostaux);
        CodePostauxDTO result = codePostauxMapper.toDto(codePostaux);
        codePostauxSearchRepository.save(codePostaux);
        return result;
    }

    /**
     * Get all the codePostauxes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CodePostauxDTO> findAll() {
        log.debug("Request to get all CodePostauxes");
        return codePostauxRepository.findAll().stream()
            .map(codePostauxMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one codePostaux by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CodePostauxDTO> findOne(Long id) {
        log.debug("Request to get CodePostaux : {}", id);
        return codePostauxRepository.findById(id)
            .map(codePostauxMapper::toDto);
    }

    /**
     * Delete the codePostaux by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CodePostaux : {}", id);
        codePostauxRepository.deleteById(id);
        codePostauxSearchRepository.deleteById(id);
    }

    /**
     * Search for the codePostaux corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CodePostauxDTO> search(String query) {
        log.debug("Request to search CodePostauxes for query {}", query);
        return StreamSupport
            .stream(codePostauxSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(codePostauxMapper::toDto)
            .collect(Collectors.toList());
    }
}
