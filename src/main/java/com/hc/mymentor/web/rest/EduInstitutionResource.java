package com.hc.mymentor.web.rest;

import com.hc.mymentor.service.EduInstitutionService;
import com.hc.mymentor.web.rest.errors.BadRequestAlertException;
import com.hc.mymentor.service.dto.EduInstitutionDTO;

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
 * REST controller for managing {@link com.hc.mymentor.domain.EduInstitution}.
 */
@RestController
@RequestMapping("/api")
public class EduInstitutionResource {

    private final Logger log = LoggerFactory.getLogger(EduInstitutionResource.class);

    private static final String ENTITY_NAME = "eduInstitution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EduInstitutionService eduInstitutionService;

    public EduInstitutionResource(EduInstitutionService eduInstitutionService) {
        this.eduInstitutionService = eduInstitutionService;
    }

    /**
     * {@code POST  /edu-institutions} : Create a new eduInstitution.
     *
     * @param eduInstitutionDTO the eduInstitutionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eduInstitutionDTO, or with status {@code 400 (Bad Request)} if the eduInstitution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/edu-institutions")
    public ResponseEntity<EduInstitutionDTO> createEduInstitution(@Valid @RequestBody EduInstitutionDTO eduInstitutionDTO) throws URISyntaxException {
        log.debug("REST request to save EduInstitution : {}", eduInstitutionDTO);
        if (eduInstitutionDTO.getId() != null) {
            throw new BadRequestAlertException("A new eduInstitution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EduInstitutionDTO result = eduInstitutionService.save(eduInstitutionDTO);
        return ResponseEntity.created(new URI("/api/edu-institutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /edu-institutions} : Updates an existing eduInstitution.
     *
     * @param eduInstitutionDTO the eduInstitutionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eduInstitutionDTO,
     * or with status {@code 400 (Bad Request)} if the eduInstitutionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eduInstitutionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/edu-institutions")
    public ResponseEntity<EduInstitutionDTO> updateEduInstitution(@Valid @RequestBody EduInstitutionDTO eduInstitutionDTO) throws URISyntaxException {
        log.debug("REST request to update EduInstitution : {}", eduInstitutionDTO);
        if (eduInstitutionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EduInstitutionDTO result = eduInstitutionService.save(eduInstitutionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eduInstitutionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /edu-institutions} : get all the eduInstitutions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eduInstitutions in body.
     */
    @GetMapping("/edu-institutions")
    public List<EduInstitutionDTO> getAllEduInstitutions() {
        log.debug("REST request to get all EduInstitutions");
        return eduInstitutionService.findAll();
    }

    /**
     * {@code GET  /edu-institutions/:id} : get the "id" eduInstitution.
     *
     * @param id the id of the eduInstitutionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eduInstitutionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/edu-institutions/{id}")
    public ResponseEntity<EduInstitutionDTO> getEduInstitution(@PathVariable Long id) {
        log.debug("REST request to get EduInstitution : {}", id);
        Optional<EduInstitutionDTO> eduInstitutionDTO = eduInstitutionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eduInstitutionDTO);
    }

    /**
     * {@code DELETE  /edu-institutions/:id} : delete the "id" eduInstitution.
     *
     * @param id the id of the eduInstitutionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/edu-institutions/{id}")
    public ResponseEntity<Void> deleteEduInstitution(@PathVariable Long id) {
        log.debug("REST request to delete EduInstitution : {}", id);
        eduInstitutionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/edu-institutions?query=:query} : search for the eduInstitution corresponding
     * to the query.
     *
     * @param query the query of the eduInstitution search.
     * @return the result of the search.
     */
    @GetMapping("/_search/edu-institutions")
    public List<EduInstitutionDTO> searchEduInstitutions(@RequestParam String query) {
        log.debug("REST request to search EduInstitutions for query {}", query);
        return eduInstitutionService.search(query);
    }
}
