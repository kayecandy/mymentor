package com.hc.mymentor.service;

import com.hc.mymentor.domain.EduStone;
import com.hc.mymentor.repository.EduStoneRepository;
import com.hc.mymentor.repository.search.EduStoneSearchRepository;
import com.hc.mymentor.service.dto.EduStoneDTO;
import com.hc.mymentor.service.mapper.EduStoneMapper;
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
 * Service Implementation for managing {@link EduStone}.
 */
@Service
@Transactional
public class EduStoneService {

    private final Logger log = LoggerFactory.getLogger(EduStoneService.class);

    private final EduStoneRepository eduStoneRepository;

    private final EduStoneMapper eduStoneMapper;

    private final EduStoneSearchRepository eduStoneSearchRepository;

    public EduStoneService(EduStoneRepository eduStoneRepository, EduStoneMapper eduStoneMapper, EduStoneSearchRepository eduStoneSearchRepository) {
        this.eduStoneRepository = eduStoneRepository;
        this.eduStoneMapper = eduStoneMapper;
        this.eduStoneSearchRepository = eduStoneSearchRepository;
    }

    /**
     * Save a eduStone.
     *
     * @param eduStoneDTO the entity to save.
     * @return the persisted entity.
     */
    public EduStoneDTO save(EduStoneDTO eduStoneDTO) {
        log.debug("Request to save EduStone : {}", eduStoneDTO);
        EduStone eduStone = eduStoneMapper.toEntity(eduStoneDTO);
        eduStone = eduStoneRepository.save(eduStone);
        EduStoneDTO result = eduStoneMapper.toDto(eduStone);
        eduStoneSearchRepository.save(eduStone);
        return result;
    }

    /**
     * Get all the eduStones.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EduStoneDTO> findAll() {
        log.debug("Request to get all EduStones");
        return eduStoneRepository.findAll().stream()
            .map(eduStoneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one eduStone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EduStoneDTO> findOne(Long id) {
        log.debug("Request to get EduStone : {}", id);
        return eduStoneRepository.findById(id)
            .map(eduStoneMapper::toDto);
    }

    /**
     * Delete the eduStone by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EduStone : {}", id);
        eduStoneRepository.deleteById(id);
        eduStoneSearchRepository.deleteById(id);
    }

    /**
     * Search for the eduStone corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EduStoneDTO> search(String query) {
        log.debug("Request to search EduStones for query {}", query);
        return StreamSupport
            .stream(eduStoneSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(eduStoneMapper::toDto)
            .collect(Collectors.toList());
    }
}
