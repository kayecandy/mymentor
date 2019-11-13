package com.hc.mymentor.web.rest;

import com.hc.mymentor.service.PossessedSkillService;
import com.hc.mymentor.web.rest.errors.BadRequestAlertException;
import com.hc.mymentor.service.dto.PossessedSkillDTO;

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
 * REST controller for managing {@link com.hc.mymentor.domain.PossessedSkill}.
 */
@RestController
@RequestMapping("/api")
public class PossessedSkillResource {

    private final Logger log = LoggerFactory.getLogger(PossessedSkillResource.class);

    private static final String ENTITY_NAME = "possessedSkill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PossessedSkillService possessedSkillService;

    public PossessedSkillResource(PossessedSkillService possessedSkillService) {
        this.possessedSkillService = possessedSkillService;
    }

    /**
     * {@code POST  /possessed-skills} : Create a new possessedSkill.
     *
     * @param possessedSkillDTO the possessedSkillDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new possessedSkillDTO, or with status {@code 400 (Bad Request)} if the possessedSkill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/possessed-skills")
    public ResponseEntity<PossessedSkillDTO> createPossessedSkill(@RequestBody PossessedSkillDTO possessedSkillDTO) throws URISyntaxException {
        log.debug("REST request to save PossessedSkill : {}", possessedSkillDTO);
        if (possessedSkillDTO.getId() != null) {
            throw new BadRequestAlertException("A new possessedSkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PossessedSkillDTO result = possessedSkillService.save(possessedSkillDTO);
        return ResponseEntity.created(new URI("/api/possessed-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /possessed-skills} : Updates an existing possessedSkill.
     *
     * @param possessedSkillDTO the possessedSkillDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated possessedSkillDTO,
     * or with status {@code 400 (Bad Request)} if the possessedSkillDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the possessedSkillDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/possessed-skills")
    public ResponseEntity<PossessedSkillDTO> updatePossessedSkill(@RequestBody PossessedSkillDTO possessedSkillDTO) throws URISyntaxException {
        log.debug("REST request to update PossessedSkill : {}", possessedSkillDTO);
        if (possessedSkillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PossessedSkillDTO result = possessedSkillService.save(possessedSkillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, possessedSkillDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /possessed-skills} : get all the possessedSkills.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of possessedSkills in body.
     */
    @GetMapping("/possessed-skills")
    public List<PossessedSkillDTO> getAllPossessedSkills() {
        log.debug("REST request to get all PossessedSkills");
        return possessedSkillService.findAll();
    }

    /**
     * {@code GET  /possessed-skills/:id} : get the "id" possessedSkill.
     *
     * @param id the id of the possessedSkillDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the possessedSkillDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/possessed-skills/{id}")
    public ResponseEntity<PossessedSkillDTO> getPossessedSkill(@PathVariable Long id) {
        log.debug("REST request to get PossessedSkill : {}", id);
        Optional<PossessedSkillDTO> possessedSkillDTO = possessedSkillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(possessedSkillDTO);
    }

    /**
     * {@code DELETE  /possessed-skills/:id} : delete the "id" possessedSkill.
     *
     * @param id the id of the possessedSkillDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/possessed-skills/{id}")
    public ResponseEntity<Void> deletePossessedSkill(@PathVariable Long id) {
        log.debug("REST request to delete PossessedSkill : {}", id);
        possessedSkillService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/possessed-skills?query=:query} : search for the possessedSkill corresponding
     * to the query.
     *
     * @param query the query of the possessedSkill search.
     * @return the result of the search.
     */
    @GetMapping("/_search/possessed-skills")
    public List<PossessedSkillDTO> searchPossessedSkills(@RequestParam String query) {
        log.debug("REST request to search PossessedSkills for query {}", query);
        return possessedSkillService.search(query);
    }
}
