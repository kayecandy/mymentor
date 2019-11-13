package com.hc.mymentor.service;

import com.hc.mymentor.domain.PossessedSkill;
import com.hc.mymentor.repository.PossessedSkillRepository;
import com.hc.mymentor.repository.search.PossessedSkillSearchRepository;
import com.hc.mymentor.service.dto.PossessedSkillDTO;
import com.hc.mymentor.service.mapper.PossessedSkillMapper;
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
 * Service Implementation for managing {@link PossessedSkill}.
 */
@Service
@Transactional
public class PossessedSkillService {

    private final Logger log = LoggerFactory.getLogger(PossessedSkillService.class);

    private final PossessedSkillRepository possessedSkillRepository;

    private final PossessedSkillMapper possessedSkillMapper;

    private final PossessedSkillSearchRepository possessedSkillSearchRepository;

    public PossessedSkillService(PossessedSkillRepository possessedSkillRepository, PossessedSkillMapper possessedSkillMapper, PossessedSkillSearchRepository possessedSkillSearchRepository) {
        this.possessedSkillRepository = possessedSkillRepository;
        this.possessedSkillMapper = possessedSkillMapper;
        this.possessedSkillSearchRepository = possessedSkillSearchRepository;
    }

    /**
     * Save a possessedSkill.
     *
     * @param possessedSkillDTO the entity to save.
     * @return the persisted entity.
     */
    public PossessedSkillDTO save(PossessedSkillDTO possessedSkillDTO) {
        log.debug("Request to save PossessedSkill : {}", possessedSkillDTO);
        PossessedSkill possessedSkill = possessedSkillMapper.toEntity(possessedSkillDTO);
        possessedSkill = possessedSkillRepository.save(possessedSkill);
        PossessedSkillDTO result = possessedSkillMapper.toDto(possessedSkill);
        possessedSkillSearchRepository.save(possessedSkill);
        return result;
    }

    /**
     * Get all the possessedSkills.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PossessedSkillDTO> findAll() {
        log.debug("Request to get all PossessedSkills");
        return possessedSkillRepository.findAll().stream()
            .map(possessedSkillMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one possessedSkill by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PossessedSkillDTO> findOne(Long id) {
        log.debug("Request to get PossessedSkill : {}", id);
        return possessedSkillRepository.findById(id)
            .map(possessedSkillMapper::toDto);
    }

    /**
     * Delete the possessedSkill by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PossessedSkill : {}", id);
        possessedSkillRepository.deleteById(id);
        possessedSkillSearchRepository.deleteById(id);
    }

    /**
     * Search for the possessedSkill corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PossessedSkillDTO> search(String query) {
        log.debug("Request to search PossessedSkills for query {}", query);
        return StreamSupport
            .stream(possessedSkillSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(possessedSkillMapper::toDto)
            .collect(Collectors.toList());
    }
}
