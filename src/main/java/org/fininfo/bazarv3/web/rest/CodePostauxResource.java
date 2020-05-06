package org.fininfo.bazarv3.web.rest;

import org.fininfo.bazarv3.service.CodePostauxService;
import org.fininfo.bazarv3.web.rest.errors.BadRequestAlertException;
import org.fininfo.bazarv3.service.dto.CodePostauxDTO;

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
 * REST controller for managing {@link org.fininfo.bazarv3.domain.CodePostaux}.
 */
@RestController
@RequestMapping("/api")
public class CodePostauxResource {

    private final Logger log = LoggerFactory.getLogger(CodePostauxResource.class);

    private static final String ENTITY_NAME = "codePostaux";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CodePostauxService codePostauxService;

    public CodePostauxResource(CodePostauxService codePostauxService) {
        this.codePostauxService = codePostauxService;
    }

    /**
     * {@code POST  /code-postauxes} : Create a new codePostaux.
     *
     * @param codePostauxDTO the codePostauxDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new codePostauxDTO, or with status {@code 400 (Bad Request)} if the codePostaux has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/code-postauxes")
    public ResponseEntity<CodePostauxDTO> createCodePostaux(@RequestBody CodePostauxDTO codePostauxDTO) throws URISyntaxException {
        log.debug("REST request to save CodePostaux : {}", codePostauxDTO);
        if (codePostauxDTO.getId() != null) {
            throw new BadRequestAlertException("A new codePostaux cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CodePostauxDTO result = codePostauxService.save(codePostauxDTO);
        return ResponseEntity.created(new URI("/api/code-postauxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /code-postauxes} : Updates an existing codePostaux.
     *
     * @param codePostauxDTO the codePostauxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codePostauxDTO,
     * or with status {@code 400 (Bad Request)} if the codePostauxDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the codePostauxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/code-postauxes")
    public ResponseEntity<CodePostauxDTO> updateCodePostaux(@RequestBody CodePostauxDTO codePostauxDTO) throws URISyntaxException {
        log.debug("REST request to update CodePostaux : {}", codePostauxDTO);
        if (codePostauxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CodePostauxDTO result = codePostauxService.save(codePostauxDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, codePostauxDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /code-postauxes} : get all the codePostauxes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of codePostauxes in body.
     */
    @GetMapping("/code-postauxes")
    public List<CodePostauxDTO> getAllCodePostauxes() {
        log.debug("REST request to get all CodePostauxes");
        return codePostauxService.findAll();
    }

    /**
     * {@code GET  /code-postauxes/:id} : get the "id" codePostaux.
     *
     * @param id the id of the codePostauxDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the codePostauxDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/code-postauxes/{id}")
    public ResponseEntity<CodePostauxDTO> getCodePostaux(@PathVariable Long id) {
        log.debug("REST request to get CodePostaux : {}", id);
        Optional<CodePostauxDTO> codePostauxDTO = codePostauxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(codePostauxDTO);
    }

    /**
     * {@code DELETE  /code-postauxes/:id} : delete the "id" codePostaux.
     *
     * @param id the id of the codePostauxDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/code-postauxes/{id}")
    public ResponseEntity<Void> deleteCodePostaux(@PathVariable Long id) {
        log.debug("REST request to delete CodePostaux : {}", id);
        codePostauxService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/code-postauxes?query=:query} : search for the codePostaux corresponding
     * to the query.
     *
     * @param query the query of the codePostaux search.
     * @return the result of the search.
     */
    @GetMapping("/_search/code-postauxes")
    public List<CodePostauxDTO> searchCodePostauxes(@RequestParam String query) {
        log.debug("REST request to search CodePostauxes for query {}", query);
        return codePostauxService.search(query);
    }
}
