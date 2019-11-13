package com.hc.mymentor.service;

import com.hc.mymentor.domain.CareerStone;
import com.hc.mymentor.repository.CareerStoneRepository;
import com.hc.mymentor.repository.search.CareerStoneSearchRepository;
import com.hc.mymentor.service.dto.CareerStoneDTO;
import com.hc.mymentor.service.mapper.CareerStoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CareerStone}.
 */
@Service
@Transactional
public class CareerStoneService {

    private final Logger log = LoggerFactory.getLogger(CareerStoneService.class);

    private final CareerStoneRepository careerStoneRepository;

    private final CareerStoneMapper careerStoneMapper;

    private final CareerStoneSearchRepository careerStoneSearchRepository;

    public CareerStoneService(CareerStoneRepository careerStoneRepository, CareerStoneMapper careerStoneMapper, CareerStoneSearchRepository careerStoneSearchRepository) {
        this.careerStoneRepository = careerStoneRepository;
        this.careerStoneMapper = careerStoneMapper;
        this.careerStoneSearchRepository = careerStoneSearchRepository;
    }

    /**
     * Save a careerStone.
     *
     * @param careerStoneDTO the entity to save.
     * @return the persisted entity.
     */
    public CareerStoneDTO save(CareerStoneDTO careerStoneDTO) {
        log.debug("Request to save CareerStone : {}", careerStoneDTO);
        CareerStone careerStone = careerStoneMapper.toEntity(careerStoneDTO);
        careerStone = careerStoneRepository.save(careerStone);
        CareerStoneDTO result = careerStoneMapper.toDto(careerStone);
        careerStoneSearchRepository.save(careerStone);
        return result;
    }

    /**
     * Get all the careerStones.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CareerStoneDTO> findAll() {
        log.debug("Request to get all CareerStones");
        return careerStoneRepository.findAll().stream()
            .map(careerStoneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one careerStone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CareerStoneDTO> findOne(Long id) {
        log.debug("Request to get CareerStone : {}", id);
        return careerStoneRepository.findById(id)
            .map(careerStoneMapper::toDto);
    }

    /**
     * Delete the careerStone by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CareerStone : {}", id);
        careerStoneRepository.deleteById(id);
        careerStoneSearchRepository.deleteById(id);
    }

    /**
     * Search for the careerStone corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CareerStoneDTO> search(String query) {
        log.debug("Request to search CareerStones for query {}", query);
        return StreamSupport
            .stream(careerStoneSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(careerStoneMapper::toDto)
            .collect(Collectors.toList());
    }
}
