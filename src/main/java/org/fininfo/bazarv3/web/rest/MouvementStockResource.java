package org.fininfo.bazarv3.web.rest;

import org.fininfo.bazarv3.service.MouvementStockService;
import org.fininfo.bazarv3.web.rest.errors.BadRequestAlertException;
import org.fininfo.bazarv3.service.dto.MouvementStockDTO;

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
 * REST controller for managing {@link org.fininfo.bazarv3.domain.MouvementStock}.
 */
@RestController
@RequestMapping("/api")
public class MouvementStockResource {

    private final Logger log = LoggerFactory.getLogger(MouvementStockResource.class);

    private static final String ENTITY_NAME = "mouvementStock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MouvementStockService mouvementStockService;

    public MouvementStockResource(MouvementStockService mouvementStockService) {
        this.mouvementStockService = mouvementStockService;
    }

    /**
     * {@code POST  /mouvement-stocks} : Create a new mouvementStock.
     *
     * @param mouvementStockDTO the mouvementStockDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mouvementStockDTO, or with status {@code 400 (Bad Request)} if the mouvementStock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mouvement-stocks")
    public ResponseEntity<MouvementStockDTO> createMouvementStock(@RequestBody MouvementStockDTO mouvementStockDTO) throws URISyntaxException {
        log.debug("REST request to save MouvementStock : {}", mouvementStockDTO);
        if (mouvementStockDTO.getId() != null) {
            throw new BadRequestAlertException("A new mouvementStock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MouvementStockDTO result = mouvementStockService.save(mouvementStockDTO);
        return ResponseEntity.created(new URI("/api/mouvement-stocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mouvement-stocks} : Updates an existing mouvementStock.
     *
     * @param mouvementStockDTO the mouvementStockDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mouvementStockDTO,
     * or with status {@code 400 (Bad Request)} if the mouvementStockDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mouvementStockDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mouvement-stocks")
    public ResponseEntity<MouvementStockDTO> updateMouvementStock(@RequestBody MouvementStockDTO mouvementStockDTO) throws URISyntaxException {
        log.debug("REST request to update MouvementStock : {}", mouvementStockDTO);
        if (mouvementStockDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MouvementStockDTO result = mouvementStockService.save(mouvementStockDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mouvementStockDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mouvement-stocks} : get all the mouvementStocks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mouvementStocks in body.
     */
    @GetMapping("/mouvement-stocks")
    public List<MouvementStockDTO> getAllMouvementStocks() {
        log.debug("REST request to get all MouvementStocks");
        return mouvementStockService.findAll();
    }

    /**
     * {@code GET  /mouvement-stocks/:id} : get the "id" mouvementStock.
     *
     * @param id the id of the mouvementStockDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mouvementStockDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mouvement-stocks/{id}")
    public ResponseEntity<MouvementStockDTO> getMouvementStock(@PathVariable Long id) {
        log.debug("REST request to get MouvementStock : {}", id);
        Optional<MouvementStockDTO> mouvementStockDTO = mouvementStockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mouvementStockDTO);
    }

    /**
     * {@code DELETE  /mouvement-stocks/:id} : delete the "id" mouvementStock.
     *
     * @param id the id of the mouvementStockDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mouvement-stocks/{id}")
    public ResponseEntity<Void> deleteMouvementStock(@PathVariable Long id) {
        log.debug("REST request to delete MouvementStock : {}", id);
        mouvementStockService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/mouvement-stocks?query=:query} : search for the mouvementStock corresponding
     * to the query.
     *
     * @param query the query of the mouvementStock search.
     * @return the result of the search.
     */
    @GetMapping("/_search/mouvement-stocks")
    public List<MouvementStockDTO> searchMouvementStocks(@RequestParam String query) {
        log.debug("REST request to search MouvementStocks for query {}", query);
        return mouvementStockService.search(query);
    }
}
