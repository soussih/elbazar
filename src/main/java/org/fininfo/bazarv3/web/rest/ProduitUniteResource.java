package org.fininfo.bazarv3.web.rest;

import org.fininfo.bazarv3.domain.ProduitUnite;
import org.fininfo.bazarv3.repository.ProduitUniteRepository;
import org.fininfo.bazarv3.repository.search.ProduitUniteSearchRepository;
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
 * REST controller for managing {@link org.fininfo.bazarv3.domain.ProduitUnite}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProduitUniteResource {

    private final Logger log = LoggerFactory.getLogger(ProduitUniteResource.class);

    private static final String ENTITY_NAME = "produitUnite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProduitUniteRepository produitUniteRepository;

    private final ProduitUniteSearchRepository produitUniteSearchRepository;

    public ProduitUniteResource(ProduitUniteRepository produitUniteRepository, ProduitUniteSearchRepository produitUniteSearchRepository) {
        this.produitUniteRepository = produitUniteRepository;
        this.produitUniteSearchRepository = produitUniteSearchRepository;
    }

    /**
     * {@code POST  /produit-unites} : Create a new produitUnite.
     *
     * @param produitUnite the produitUnite to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produitUnite, or with status {@code 400 (Bad Request)} if the produitUnite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/produit-unites")
    public ResponseEntity<ProduitUnite> createProduitUnite(@RequestBody ProduitUnite produitUnite) throws URISyntaxException {
        log.debug("REST request to save ProduitUnite : {}", produitUnite);
        if (produitUnite.getId() != null) {
            throw new BadRequestAlertException("A new produitUnite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProduitUnite result = produitUniteRepository.save(produitUnite);
        produitUniteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/produit-unites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /produit-unites} : Updates an existing produitUnite.
     *
     * @param produitUnite the produitUnite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produitUnite,
     * or with status {@code 400 (Bad Request)} if the produitUnite is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produitUnite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/produit-unites")
    public ResponseEntity<ProduitUnite> updateProduitUnite(@RequestBody ProduitUnite produitUnite) throws URISyntaxException {
        log.debug("REST request to update ProduitUnite : {}", produitUnite);
        if (produitUnite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProduitUnite result = produitUniteRepository.save(produitUnite);
        produitUniteSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, produitUnite.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /produit-unites} : get all the produitUnites.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produitUnites in body.
     */
    @GetMapping("/produit-unites")
    public List<ProduitUnite> getAllProduitUnites() {
        log.debug("REST request to get all ProduitUnites");
        return produitUniteRepository.findAll();
    }

    /**
     * {@code GET  /produit-unites/:id} : get the "id" produitUnite.
     *
     * @param id the id of the produitUnite to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produitUnite, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/produit-unites/{id}")
    public ResponseEntity<ProduitUnite> getProduitUnite(@PathVariable Long id) {
        log.debug("REST request to get ProduitUnite : {}", id);
        Optional<ProduitUnite> produitUnite = produitUniteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(produitUnite);
    }

    /**
     * {@code DELETE  /produit-unites/:id} : delete the "id" produitUnite.
     *
     * @param id the id of the produitUnite to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/produit-unites/{id}")
    public ResponseEntity<Void> deleteProduitUnite(@PathVariable Long id) {
        log.debug("REST request to delete ProduitUnite : {}", id);
        produitUniteRepository.deleteById(id);
        produitUniteSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/produit-unites?query=:query} : search for the produitUnite corresponding
     * to the query.
     *
     * @param query the query of the produitUnite search.
     * @return the result of the search.
     */
    @GetMapping("/_search/produit-unites")
    public List<ProduitUnite> searchProduitUnites(@RequestParam String query) {
        log.debug("REST request to search ProduitUnites for query {}", query);
        return StreamSupport
            .stream(produitUniteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
