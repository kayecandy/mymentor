package com.hc.mymentor.web.rest;

import com.hc.mymentor.service.CareerStoneService;
import com.hc.mymentor.web.rest.errors.BadRequestAlertException;
import com.hc.mymentor.service.dto.CareerStoneDTO;

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
 * REST controller for managing {@link com.hc.mymentor.domain.CareerStone}.
 */
@RestController
@RequestMapping("/api")
public class CareerStoneResource {

    private final Logger log = LoggerFactory.getLogger(CareerStoneResource.class);

    private static final String ENTITY_NAME = "careerStone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CareerStoneService careerStoneService;

    public CareerStoneResource(CareerStoneService careerStoneService) {
        this.careerStoneService = careerStoneService;
    }

    /**
     * {@code POST  /career-stones} : Create a new careerStone.
     *
     * @param careerStoneDTO the careerStoneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new careerStoneDTO, or with status {@code 400 (Bad Request)} if the careerStone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/career-stones")
    public ResponseEntity<CareerStoneDTO> createCareerStone(@Valid @RequestBody CareerStoneDTO careerStoneDTO) throws URISyntaxException {
        log.debug("REST request to save CareerStone : {}", careerStoneDTO);
        if (careerStoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new careerStone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CareerStoneDTO result = careerStoneService.save(careerStoneDTO);
        return ResponseEntity.created(new URI("/api/career-stones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /career-stones} : Updates an existing careerStone.
     *
     * @param careerStoneDTO the careerStoneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated careerStoneDTO,
     * or with status {@code 400 (Bad Request)} if the careerStoneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the careerStoneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/career-stones")
    public ResponseEntity<CareerStoneDTO> updateCareerStone(@Valid @RequestBody CareerStoneDTO careerStoneDTO) throws URISyntaxException {
        log.debug("REST request to update CareerStone : {}", careerStoneDTO);
        if (careerStoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CareerStoneDTO result = careerStoneService.save(careerStoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, careerStoneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /career-stones} : get all the careerStones.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of careerStones in body.
     */
    @GetMapping("/career-stones")
    public List<CareerStoneDTO> getAllCareerStones() {
        log.debug("REST request to get all CareerStones");
        return careerStoneService.findAll();
    }

    /**
     * {@code GET  /career-stones/:id} : get the "id" careerStone.
     *
     * @param id the id of the careerStoneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the careerStoneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/career-stones/{id}")
    public ResponseEntity<CareerStoneDTO> getCareerStone(@PathVariable Long id) {
        log.debug("REST request to get CareerStone : {}", id);
        Optional<CareerStoneDTO> careerStoneDTO = careerStoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(careerStoneDTO);
    }

    /**
     * {@code DELETE  /career-stones/:id} : delete the "id" careerStone.
     *
     * @param id the id of the careerStoneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/career-stones/{id}")
    public ResponseEntity<Void> deleteCareerStone(@PathVariable Long id) {
        log.debug("REST request to delete CareerStone : {}", id);
        careerStoneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/career-stones?query=:query} : search for the careerStone corresponding
     * to the query.
     *
     * @param query the query of the careerStone search.
     * @return the result of the search.
     */
    @GetMapping("/_search/career-stones")
    public List<CareerStoneDTO> searchCareerStones(@RequestParam String query) {
        log.debug("REST request to search CareerStones for query {}", query);
        return careerStoneService.search(query);
    }
}
