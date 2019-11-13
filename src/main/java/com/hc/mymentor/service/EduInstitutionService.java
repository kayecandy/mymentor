package com.hc.mymentor.service;

import com.hc.mymentor.domain.EduInstitution;
import com.hc.mymentor.repository.EduInstitutionRepository;
import com.hc.mymentor.repository.search.EduInstitutionSearchRepository;
import com.hc.mymentor.service.dto.EduInstitutionDTO;
import com.hc.mymentor.service.mapper.EduInstitutionMapper;
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
 * Service Implementation for managing {@link EduInstitution}.
 */
@Service
@Transactional
public class EduInstitutionService {

    private final Logger log = LoggerFactory.getLogger(EduInstitutionService.class);

    private final EduInstitutionRepository eduInstitutionRepository;

    private final EduInstitutionMapper eduInstitutionMapper;

    private final EduInstitutionSearchRepository eduInstitutionSearchRepository;

    public EduInstitutionService(EduInstitutionRepository eduInstitutionRepository, EduInstitutionMapper eduInstitutionMapper, EduInstitutionSearchRepository eduInstitutionSearchRepository) {
        this.eduInstitutionRepository = eduInstitutionRepository;
        this.eduInstitutionMapper = eduInstitutionMapper;
        this.eduInstitutionSearchRepository = eduInstitutionSearchRepository;
    }

    /**
     * Save a eduInstitution.
     *
     * @param eduInstitutionDTO the entity to save.
     * @return the persisted entity.
     */
    public EduInstitutionDTO save(EduInstitutionDTO eduInstitutionDTO) {
        log.debug("Request to save EduInstitution : {}", eduInstitutionDTO);
        EduInstitution eduInstitution = eduInstitutionMapper.toEntity(eduInstitutionDTO);
        eduInstitution = eduInstitutionRepository.save(eduInstitution);
        EduInstitutionDTO result = eduInstitutionMapper.toDto(eduInstitution);
        eduInstitutionSearchRepository.save(eduInstitution);
        return result;
    }

    /**
     * Get all the eduInstitutions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EduInstitutionDTO> findAll() {
        log.debug("Request to get all EduInstitutions");
        return eduInstitutionRepository.findAll().stream()
            .map(eduInstitutionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one eduInstitution by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EduInstitutionDTO> findOne(Long id) {
        log.debug("Request to get EduInstitution : {}", id);
        return eduInstitutionRepository.findById(id)
            .map(eduInstitutionMapper::toDto);
    }

    /**
     * Delete the eduInstitution by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EduInstitution : {}", id);
        eduInstitutionRepository.deleteById(id);
        eduInstitutionSearchRepository.deleteById(id);
    }

    /**
     * Search for the eduInstitution corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EduInstitutionDTO> search(String query) {
        log.debug("Request to search EduInstitutions for query {}", query);
        return StreamSupport
            .stream(eduInstitutionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(eduInstitutionMapper::toDto)
            .collect(Collectors.toList());
    }
}
