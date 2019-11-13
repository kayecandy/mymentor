package com.hc.mymentor.service;

import com.hc.mymentor.domain.Mentee;
import com.hc.mymentor.repository.MenteeRepository;
import com.hc.mymentor.repository.search.MenteeSearchRepository;
import com.hc.mymentor.service.dto.MenteeDTO;
import com.hc.mymentor.service.mapper.MenteeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Mentee}.
 */
@Service
@Transactional
public class MenteeService {

    private final Logger log = LoggerFactory.getLogger(MenteeService.class);

    private final MenteeRepository menteeRepository;

    private final MenteeMapper menteeMapper;

    private final MenteeSearchRepository menteeSearchRepository;

    public MenteeService(MenteeRepository menteeRepository, MenteeMapper menteeMapper, MenteeSearchRepository menteeSearchRepository) {
        this.menteeRepository = menteeRepository;
        this.menteeMapper = menteeMapper;
        this.menteeSearchRepository = menteeSearchRepository;
    }

    /**
     * Save a mentee.
     *
     * @param menteeDTO the entity to save.
     * @return the persisted entity.
     */
    public MenteeDTO save(MenteeDTO menteeDTO) {
        log.debug("Request to save Mentee : {}", menteeDTO);
        Mentee mentee = menteeMapper.toEntity(menteeDTO);
        mentee = menteeRepository.save(mentee);
        MenteeDTO result = menteeMapper.toDto(mentee);
        menteeSearchRepository.save(mentee);
        return result;
    }

    /**
     * Get all the mentees.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MenteeDTO> findAll() {
        log.debug("Request to get all Mentees");
        return menteeRepository.findAllWithEagerRelationships().stream()
            .map(menteeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the mentees with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<MenteeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return menteeRepository.findAllWithEagerRelationships(pageable).map(menteeMapper::toDto);
    }
    

    /**
     * Get one mentee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MenteeDTO> findOne(Long id) {
        log.debug("Request to get Mentee : {}", id);
        return menteeRepository.findOneWithEagerRelationships(id)
            .map(menteeMapper::toDto);
    }

    /**
     * Delete the mentee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Mentee : {}", id);
        menteeRepository.deleteById(id);
        menteeSearchRepository.deleteById(id);
    }

    /**
     * Search for the mentee corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MenteeDTO> search(String query) {
        log.debug("Request to search Mentees for query {}", query);
        return StreamSupport
            .stream(menteeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(menteeMapper::toDto)
            .collect(Collectors.toList());
    }
}
