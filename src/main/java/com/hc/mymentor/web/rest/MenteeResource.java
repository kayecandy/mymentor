package com.hc.mymentor.web.rest;

import com.hc.mymentor.service.MenteeService;
import com.hc.mymentor.web.rest.errors.BadRequestAlertException;
import com.hc.mymentor.service.dto.MenteeDTO;

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
 * REST controller for managing {@link com.hc.mymentor.domain.Mentee}.
 */
@RestController
@RequestMapping("/api")
public class MenteeResource {

    private final Logger log = LoggerFactory.getLogger(MenteeResource.class);

    private static final String ENTITY_NAME = "mentee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MenteeService menteeService;

    public MenteeResource(MenteeService menteeService) {
        this.menteeService = menteeService;
    }

    /**
     * {@code POST  /mentees} : Create a new mentee.
     *
     * @param menteeDTO the menteeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new menteeDTO, or with status {@code 400 (Bad Request)} if the mentee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mentees")
    public ResponseEntity<MenteeDTO> createMentee(@Valid @RequestBody MenteeDTO menteeDTO) throws URISyntaxException {
        log.debug("REST request to save Mentee : {}", menteeDTO);
        if (menteeDTO.getId() != null) {
            throw new BadRequestAlertException("A new mentee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MenteeDTO result = menteeService.save(menteeDTO);
        return ResponseEntity.created(new URI("/api/mentees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mentees} : Updates an existing mentee.
     *
     * @param menteeDTO the menteeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated menteeDTO,
     * or with status {@code 400 (Bad Request)} if the menteeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the menteeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mentees")
    public ResponseEntity<MenteeDTO> updateMentee(@Valid @RequestBody MenteeDTO menteeDTO) throws URISyntaxException {
        log.debug("REST request to update Mentee : {}", menteeDTO);
        if (menteeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MenteeDTO result = menteeService.save(menteeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, menteeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mentees} : get all the mentees.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mentees in body.
     */
    @GetMapping("/mentees")
    public List<MenteeDTO> getAllMentees(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Mentees");
        return menteeService.findAll();
    }

    /**
     * {@code GET  /mentees/:id} : get the "id" mentee.
     *
     * @param id the id of the menteeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the menteeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mentees/{id}")
    public ResponseEntity<MenteeDTO> getMentee(@PathVariable Long id) {
        log.debug("REST request to get Mentee : {}", id);
        Optional<MenteeDTO> menteeDTO = menteeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(menteeDTO);
    }

    /**
     * {@code DELETE  /mentees/:id} : delete the "id" mentee.
     *
     * @param id the id of the menteeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mentees/{id}")
    public ResponseEntity<Void> deleteMentee(@PathVariable Long id) {
        log.debug("REST request to delete Mentee : {}", id);
        menteeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/mentees?query=:query} : search for the mentee corresponding
     * to the query.
     *
     * @param query the query of the mentee search.
     * @return the result of the search.
     */
    @GetMapping("/_search/mentees")
    public List<MenteeDTO> searchMentees(@RequestParam String query) {
        log.debug("REST request to search Mentees for query {}", query);
        return menteeService.search(query);
    }
}
