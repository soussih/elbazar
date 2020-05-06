package org.fininfo.bazarv3.service;

import org.fininfo.bazarv3.domain.Images;
import org.fininfo.bazarv3.repository.ImagesRepository;
import org.fininfo.bazarv3.repository.search.ImagesSearchRepository;
import org.fininfo.bazarv3.service.dto.ImagesDTO;
import org.fininfo.bazarv3.service.mapper.ImagesMapper;
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
 * Service Implementation for managing {@link Images}.
 */
@Service
@Transactional
public class ImagesService {

    private final Logger log = LoggerFactory.getLogger(ImagesService.class);

    private final ImagesRepository imagesRepository;

    private final ImagesMapper imagesMapper;

    private final ImagesSearchRepository imagesSearchRepository;

    public ImagesService(ImagesRepository imagesRepository, ImagesMapper imagesMapper, ImagesSearchRepository imagesSearchRepository) {
        this.imagesRepository = imagesRepository;
        this.imagesMapper = imagesMapper;
        this.imagesSearchRepository = imagesSearchRepository;
    }

    /**
     * Save a images.
     *
     * @param imagesDTO the entity to save.
     * @return the persisted entity.
     */
    public ImagesDTO save(ImagesDTO imagesDTO) {
        log.debug("Request to save Images : {}", imagesDTO);
        Images images = imagesMapper.toEntity(imagesDTO);
        images = imagesRepository.save(images);
        ImagesDTO result = imagesMapper.toDto(images);
        imagesSearchRepository.save(images);
        return result;
    }

    /**
     * Get all the images.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ImagesDTO> findAll() {
        log.debug("Request to get all Images");
        return imagesRepository.findAll().stream()
            .map(imagesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one images by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ImagesDTO> findOne(Long id) {
        log.debug("Request to get Images : {}", id);
        return imagesRepository.findById(id)
            .map(imagesMapper::toDto);
    }

    /**
     * Delete the images by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Images : {}", id);
        imagesRepository.deleteById(id);
        imagesSearchRepository.deleteById(id);
    }

    /**
     * Search for the images corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ImagesDTO> search(String query) {
        log.debug("Request to search Images for query {}", query);
        return StreamSupport
            .stream(imagesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(imagesMapper::toDto)
            .collect(Collectors.toList());
    }
}
