package com.hc.mymentor.web.rest;

import com.hc.mymentor.service.EduStoneService;
import com.hc.mymentor.web.rest.errors.BadRequestAlertException;
import com.hc.mymentor.service.dto.EduStoneDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.hc.mymentor.domain.EduStone}.
 */
@RestController
@RequestMapping("/api")
public class EduStoneResource {

    private final Logger log = LoggerFactory.getLogger(EduStoneResource.class);

    private static final String ENTITY_NAME = "eduStone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EduStoneService eduStoneService;

    public EduStoneResource(EduStoneService eduStoneService) {
        this.eduStoneService = eduStoneService;
    }

    /**
     * {@code POST  /edu-stones} : Create a new eduStone.
     *
     * @param eduStoneDTO the eduStoneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eduStoneDTO, or with status {@code 400 (Bad Request)} if the eduStone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/edu-stones")
    public ResponseEntity<EduStoneDTO> createEduStone(@Valid @RequestBody EduStoneDTO eduStoneDTO) throws URISyntaxException {
        log.debug("REST request to save EduStone : {}", eduStoneDTO);
        if (eduStoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new eduStone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EduStoneDTO result = eduStoneService.save(eduStoneDTO);
        return ResponseEntity.created(new URI("/api/edu-stones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /edu-stones} : Updates an existing eduStone.
     *
     * @param eduStoneDTO the eduStoneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eduStoneDTO,
     * or with status {@code 400 (Bad Request)} if the eduStoneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eduStoneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/edu-stones")
    public ResponseEntity<EduStoneDTO> updateEduStone(@Valid @RequestBody EduStoneDTO eduStoneDTO) throws URISyntaxException {
        log.debug("REST request to update EduStone : {}", eduStoneDTO);
        if (eduStoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EduStoneDTO result = eduStoneService.save(eduStoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eduStoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /edu-stones} : get all the eduStones.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eduStones in body.
     */
    @GetMapping("/edu-stones")
    public List<EduStoneDTO> getAllEduStones() {
        log.debug("REST request to get all EduStones");
        return eduStoneService.findAll();
    }

    /**
     * {@code GET  /edu-stones/:id} : get the "id" eduStone.
     *
     * @param id the id of the eduStoneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eduStoneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/edu-stones/{id}")
    public ResponseEntity<EduStoneDTO> getEduStone(@PathVariable Long id) {
        log.debug("REST request to get EduStone : {}", id);
        Optional<EduStoneDTO> eduStoneDTO = eduStoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eduStoneDTO);
    }

    /**
     * {@code DELETE  /edu-stones/:id} : delete the "id" eduStone.
     *
     * @param id the id of the eduStoneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/edu-stones/{id}")
    public ResponseEntity<Void> deleteEduStone(@PathVariable Long id) {
        log.debug("REST request to delete EduStone : {}", id);
        eduStoneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/edu-stones?query=:query} : search for the eduStone corresponding
     * to the query.
     *
     * @param query the query of the eduStone search.
     * @return the result of the search.
     */
    @GetMapping("/_search/edu-stones")
    public List<EduStoneDTO> searchEduStones(@RequestParam String query) {
        log.debug("REST request to search EduStones for query {}", query);
        return eduStoneService.search(query);
    }
}
