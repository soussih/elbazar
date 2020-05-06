package org.fininfo.bazarv3.web.rest;

import org.fininfo.bazarv3.domain.ProdEnumeration;
import org.fininfo.bazarv3.repository.ProdEnumerationRepository;
import org.fininfo.bazarv3.repository.search.ProdEnumerationSearchRepository;
import org.fininfo.bazarv3.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link org.fininfo.bazarv3.domain.ProdEnumeration}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProdEnumerationResource {

    private final Logger log = LoggerFactory.getLogger(ProdEnumerationResource.class);

    private static final String ENTITY_NAME = "prodEnumeration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProdEnumerationRepository prodEnumerationRepository;

    private final ProdEnumerationSearchRepository prodEnumerationSearchRepository;

    public ProdEnumerationResource(ProdEnumerationRepository prodEnumerationRepository, ProdEnumerationSearchRepository prodEnumerationSearchRepository) {
        this.prodEnumerationRepository = prodEnumerationRepository;
        this.prodEnumerationSearchRepository = prodEnumerationSearchRepository;
    }

    /**
     * {@code POST  /prod-enumerations} : Create a new prodEnumeration.
     *
     * @param prodEnumeration the prodEnumeration to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prodEnumeration, or with status {@code 400 (Bad Request)} if the prodEnumeration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prod-enumerations")
    public ResponseEntity<ProdEnumeration> createProdEnumeration(@RequestBody ProdEnumeration prodEnumeration) throws URISyntaxException {
        log.debug("REST request to save ProdEnumeration : {}", prodEnumeration);
        if (prodEnumeration.getId() != null) {
            throw new BadRequestAlertException("A new prodEnumeration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProdEnumeration result = prodEnumerationRepository.save(prodEnumeration);
        prodEnumerationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prod-enumerations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prod-enumerations} : Updates an existing prodEnumeration.
     *
     * @param prodEnumeration the prodEnumeration to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prodEnumeration,
     * or with status {@code 400 (Bad Request)} if the prodEnumeration is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prodEnumeration couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prod-enumerations")
    public ResponseEntity<ProdEnumeration> updateProdEnumeration(@RequestBody ProdEnumeration prodEnumeration) throws URISyntaxException {
        log.debug("REST request to update ProdEnumeration : {}", prodEnumeration);
        if (prodEnumeration.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProdEnumeration result = prodEnumerationRepository.save(prodEnumeration);
        prodEnumerationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prodEnumeration.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prod-enumerations} : get all the prodEnumerations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prodEnumerations in body.
     */
    @GetMapping("/prod-enumerations")
    public List<ProdEnumeration> getAllProdEnumerations() {
        log.debug("REST request to get all ProdEnumerations");
        return prodEnumerationRepository.findAll();
    }

    /**
     * {@code GET  /prod-enumerations/:id} : get the "id" prodEnumeration.
     *
     * @param id the id of the prodEnumeration to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prodEnumeration, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prod-enumerations/{id}")
    public ResponseEntity<ProdEnumeration> getProdEnumeration(@PathVariable Long id) {
        log.debug("REST request to get ProdEnumeration : {}", id);
        Optional<ProdEnumeration> prodEnumeration = prodEnumerationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prodEnumeration);
    }

    /**
     * {@code DELETE  /prod-enumerations/:id} : delete the "id" prodEnumeration.
     *
     * @param id the id of the prodEnumeration to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prod-enumerations/{id}")
    public ResponseEntity<Void> deleteProdEnumeration(@PathVariable Long id) {
        log.debug("REST request to delete ProdEnumeration : {}", id);
        prodEnumerationRepository.deleteById(id);
        prodEnumerationSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/prod-enumerations?query=:query} : search for the prodEnumeration corresponding
     * to the query.
     *
     * @param query the query of the prodEnumeration search.
     * @return the result of the search.
     */
    @GetMapping("/_search/prod-enumerations")
    public List<ProdEnumeration> searchProdEnumerations(@RequestParam String query) {
        log.debug("REST request to search ProdEnumerations for query {}", query);
        return StreamSupport
            .stream(prodEnumerationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
