package org.fininfo.bazarv3.web.rest;

import org.fininfo.bazarv3.service.RemiseService;
import org.fininfo.bazarv3.web.rest.errors.BadRequestAlertException;
import org.fininfo.bazarv3.service.dto.RemiseDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link org.fininfo.bazarv3.domain.Remise}.
 */
@RestController
@RequestMapping("/api")
public class RemiseResource {

    private final Logger log = LoggerFactory.getLogger(RemiseResource.class);

    private static final String ENTITY_NAME = "remise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RemiseService remiseService;

    public RemiseResource(RemiseService remiseService) {
        this.remiseService = remiseService;
    }

    /**
     * {@code POST  /remises} : Create a new remise.
     *
     * @param remiseDTO the remiseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new remiseDTO, or with status {@code 400 (Bad Request)} if the remise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/remises")
    public ResponseEntity<RemiseDTO> createRemise(@RequestBody RemiseDTO remiseDTO) throws URISyntaxException {
        log.debug("REST request to save Remise : {}", remiseDTO);
        if (remiseDTO.getId() != null) {
            throw new BadRequestAlertException("A new remise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RemiseDTO result = remiseService.save(remiseDTO);
        return ResponseEntity.created(new URI("/api/remises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /remises} : Updates an existing remise.
     *
     * @param remiseDTO the remiseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remiseDTO,
     * or with status {@code 400 (Bad Request)} if the remiseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the remiseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/remises")
    public ResponseEntity<RemiseDTO> updateRemise(@RequestBody RemiseDTO remiseDTO) throws URISyntaxException {
        log.debug("REST request to update Remise : {}", remiseDTO);
        if (remiseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RemiseDTO result = remiseService.save(remiseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, remiseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /remises} : get all the remises.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of remises in body.
     */
    @GetMapping("/remises")
    public List<RemiseDTO> getAllRemises() {
        log.debug("REST request to get all Remises");
        return remiseService.findAll();
    }

    /**
     * {@code GET  /remises/:id} : get the "id" remise.
     *
     * @param id the id of the remiseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the remiseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/remises/{id}")
    public ResponseEntity<RemiseDTO> getRemise(@PathVariable Long id) {
        log.debug("REST request to get Remise : {}", id);
        Optional<RemiseDTO> remiseDTO = remiseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(remiseDTO);
    }

    /**
     * {@code DELETE  /remises/:id} : delete the "id" remise.
     *
     * @param id the id of the remiseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/remises/{id}")
    public ResponseEntity<Void> deleteRemise(@PathVariable Long id) {
        log.debug("REST request to delete Remise : {}", id);
        remiseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/remises?query=:query} : search for the remise corresponding
     * to the query.
     *
     * @param query the query of the remise search.
     * @return the result of the search.
     */
    @GetMapping("/_search/remises")
    public List<RemiseDTO> searchRemises(@RequestParam String query) {
        log.debug("REST request to search Remises for query {}", query);
        return remiseService.search(query);
    }
}
