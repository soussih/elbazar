package org.fininfo.bazarv3.service;

import org.fininfo.bazarv3.domain.CommandeLignes;
import org.fininfo.bazarv3.repository.CommandeLignesRepository;
import org.fininfo.bazarv3.repository.search.CommandeLignesSearchRepository;
import org.fininfo.bazarv3.service.dto.CommandeLignesDTO;
import org.fininfo.bazarv3.service.mapper.CommandeLignesMapper;
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
 * Service Implementation for managing {@link CommandeLignes}.
 */
@Service
@Transactional
public class CommandeLignesService {

    private final Logger log = LoggerFactory.getLogger(CommandeLignesService.class);

    private final CommandeLignesRepository commandeLignesRepository;

    private final CommandeLignesMapper commandeLignesMapper;

    private final CommandeLignesSearchRepository commandeLignesSearchRepository;

    public CommandeLignesService(CommandeLignesRepository commandeLignesRepository, CommandeLignesMapper commandeLignesMapper, CommandeLignesSearchRepository commandeLignesSearchRepository) {
        this.commandeLignesRepository = commandeLignesRepository;
        this.commandeLignesMapper = commandeLignesMapper;
        this.commandeLignesSearchRepository = commandeLignesSearchRepository;
    }

    /**
     * Save a commandeLignes.
     *
     * @param commandeLignesDTO the entity to save.
     * @return the persisted entity.
     */
    public CommandeLignesDTO save(CommandeLignesDTO commandeLignesDTO) {
        log.debug("Request to save CommandeLignes : {}", commandeLignesDTO);
        CommandeLignes commandeLignes = commandeLignesMapper.toEntity(commandeLignesDTO);
        commandeLignes = commandeLignesRepository.save(commandeLignes);
        CommandeLignesDTO result = commandeLignesMapper.toDto(commandeLignes);
        commandeLignesSearchRepository.save(commandeLignes);
        return result;
    }

    /**
     * Get all the commandeLignes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CommandeLignesDTO> findAll() {
        log.debug("Request to get all CommandeLignes");
        return commandeLignesRepository.findAll().stream()
            .map(commandeLignesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one commandeLignes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommandeLignesDTO> findOne(Long id) {
        log.debug("Request to get CommandeLignes : {}", id);
        return commandeLignesRepository.findById(id)
            .map(commandeLignesMapper::toDto);
    }

    /**
     * Delete the commandeLignes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommandeLignes : {}", id);
        commandeLignesRepository.deleteById(id);
        commandeLignesSearchRepository.deleteById(id);
    }

    /**
     * Search for the commandeLignes corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CommandeLignesDTO> search(String query) {
        log.debug("Request to search CommandeLignes for query {}", query);
        return StreamSupport
            .stream(commandeLignesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(commandeLignesMapper::toDto)
            .collect(Collectors.toList());
    }
}
