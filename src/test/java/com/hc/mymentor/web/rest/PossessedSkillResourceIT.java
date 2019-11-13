package com.hc.mymentor.web.rest;

import com.hc.mymentor.MymentorApp;
import com.hc.mymentor.domain.PossessedSkill;
import com.hc.mymentor.repository.PossessedSkillRepository;
import com.hc.mymentor.repository.search.PossessedSkillSearchRepository;
import com.hc.mymentor.service.PossessedSkillService;
import com.hc.mymentor.service.dto.PossessedSkillDTO;
import com.hc.mymentor.service.mapper.PossessedSkillMapper;
import com.hc.mymentor.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.hc.mymentor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PossessedSkillResource} REST controller.
 */
@SpringBootTest(classes = MymentorApp.class)
public class PossessedSkillResourceIT {

    private static final Boolean DEFAULT_TOP_SKILL = false;
    private static final Boolean UPDATED_TOP_SKILL = true;

    @Autowired
    private PossessedSkillRepository possessedSkillRepository;

    @Autowired
    private PossessedSkillMapper possessedSkillMapper;

    @Autowired
    private PossessedSkillService possessedSkillService;

    /**
     * This repository is mocked in the com.hc.mymentor.repository.search test package.
     *
     * @see com.hc.mymentor.repository.search.PossessedSkillSearchRepositoryMockConfiguration
     */
    @Autowired
    private PossessedSkillSearchRepository mockPossessedSkillSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPossessedSkillMockMvc;

    private PossessedSkill possessedSkill;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PossessedSkillResource possessedSkillResource = new PossessedSkillResource(possessedSkillService);
        this.restPossessedSkillMockMvc = MockMvcBuilders.standaloneSetup(possessedSkillResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PossessedSkill createEntity(EntityManager em) {
        PossessedSkill possessedSkill = new PossessedSkill()
            .topSkill(DEFAULT_TOP_SKILL);
        return possessedSkill;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PossessedSkill createUpdatedEntity(EntityManager em) {
        PossessedSkill possessedSkill = new PossessedSkill()
            .topSkill(UPDATED_TOP_SKILL);
        return possessedSkill;
    }

    @BeforeEach
    public void initTest() {
        possessedSkill = createEntity(em);
    }

    @Test
    @Transactional
    public void createPossessedSkill() throws Exception {
        int databaseSizeBeforeCreate = possessedSkillRepository.findAll().size();

        // Create the PossessedSkill
        PossessedSkillDTO possessedSkillDTO = possessedSkillMapper.toDto(possessedSkill);
        restPossessedSkillMockMvc.perform(post("/api/possessed-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possessedSkillDTO)))
            .andExpect(status().isCreated());

        // Validate the PossessedSkill in the database
        List<PossessedSkill> possessedSkillList = possessedSkillRepository.findAll();
        assertThat(possessedSkillList).hasSize(databaseSizeBeforeCreate + 1);
        PossessedSkill testPossessedSkill = possessedSkillList.get(possessedSkillList.size() - 1);
        assertThat(testPossessedSkill.isTopSkill()).isEqualTo(DEFAULT_TOP_SKILL);

        // Validate the PossessedSkill in Elasticsearch
        verify(mockPossessedSkillSearchRepository, times(1)).save(testPossessedSkill);
    }

    @Test
    @Transactional
    public void createPossessedSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = possessedSkillRepository.findAll().size();

        // Create the PossessedSkill with an existing ID
        possessedSkill.setId(1L);
        PossessedSkillDTO possessedSkillDTO = possessedSkillMapper.toDto(possessedSkill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPossessedSkillMockMvc.perform(post("/api/possessed-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possessedSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PossessedSkill in the database
        List<PossessedSkill> possessedSkillList = possessedSkillRepository.findAll();
        assertThat(possessedSkillList).hasSize(databaseSizeBeforeCreate);

        // Validate the PossessedSkill in Elasticsearch
        verify(mockPossessedSkillSearchRepository, times(0)).save(possessedSkill);
    }


    @Test
    @Transactional
    public void getAllPossessedSkills() throws Exception {
        // Initialize the database
        possessedSkillRepository.saveAndFlush(possessedSkill);

        // Get all the possessedSkillList
        restPossessedSkillMockMvc.perform(get("/api/possessed-skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(possessedSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].topSkill").value(hasItem(DEFAULT_TOP_SKILL.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPossessedSkill() throws Exception {
        // Initialize the database
        possessedSkillRepository.saveAndFlush(possessedSkill);

        // Get the possessedSkill
        restPossessedSkillMockMvc.perform(get("/api/possessed-skills/{id}", possessedSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(possessedSkill.getId().intValue()))
            .andExpect(jsonPath("$.topSkill").value(DEFAULT_TOP_SKILL.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPossessedSkill() throws Exception {
        // Get the possessedSkill
        restPossessedSkillMockMvc.perform(get("/api/possessed-skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePossessedSkill() throws Exception {
        // Initialize the database
        possessedSkillRepository.saveAndFlush(possessedSkill);

        int databaseSizeBeforeUpdate = possessedSkillRepository.findAll().size();

        // Update the possessedSkill
        PossessedSkill updatedPossessedSkill = possessedSkillRepository.findById(possessedSkill.getId()).get();
        // Disconnect from session so that the updates on updatedPossessedSkill are not directly saved in db
        em.detach(updatedPossessedSkill);
        updatedPossessedSkill
            .topSkill(UPDATED_TOP_SKILL);
        PossessedSkillDTO possessedSkillDTO = possessedSkillMapper.toDto(updatedPossessedSkill);

        restPossessedSkillMockMvc.perform(put("/api/possessed-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possessedSkillDTO)))
            .andExpect(status().isOk());

        // Validate the PossessedSkill in the database
        List<PossessedSkill> possessedSkillList = possessedSkillRepository.findAll();
        assertThat(possessedSkillList).hasSize(databaseSizeBeforeUpdate);
        PossessedSkill testPossessedSkill = possessedSkillList.get(possessedSkillList.size() - 1);
        assertThat(testPossessedSkill.isTopSkill()).isEqualTo(UPDATED_TOP_SKILL);

        // Validate the PossessedSkill in Elasticsearch
        verify(mockPossessedSkillSearchRepository, times(1)).save(testPossessedSkill);
    }

    @Test
    @Transactional
    public void updateNonExistingPossessedSkill() throws Exception {
        int databaseSizeBeforeUpdate = possessedSkillRepository.findAll().size();

        // Create the PossessedSkill
        PossessedSkillDTO possessedSkillDTO = possessedSkillMapper.toDto(possessedSkill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPossessedSkillMockMvc.perform(put("/api/possessed-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possessedSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PossessedSkill in the database
        List<PossessedSkill> possessedSkillList = possessedSkillRepository.findAll();
        assertThat(possessedSkillList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PossessedSkill in Elasticsearch
        verify(mockPossessedSkillSearchRepository, times(0)).save(possessedSkill);
    }

    @Test
    @Transactional
    public void deletePossessedSkill() throws Exception {
        // Initialize the database
        possessedSkillRepository.saveAndFlush(possessedSkill);

        int databaseSizeBeforeDelete = possessedSkillRepository.findAll().size();

        // Delete the possessedSkill
        restPossessedSkillMockMvc.perform(delete("/api/possessed-skills/{id}", possessedSkill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PossessedSkill> possessedSkillList = possessedSkillRepository.findAll();
        assertThat(possessedSkillList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PossessedSkill in Elasticsearch
        verify(mockPossessedSkillSearchRepository, times(1)).deleteById(possessedSkill.getId());
    }

    @Test
    @Transactional
    public void searchPossessedSkill() throws Exception {
        // Initialize the database
        possessedSkillRepository.saveAndFlush(possessedSkill);
        when(mockPossessedSkillSearchRepository.search(queryStringQuery("id:" + possessedSkill.getId())))
            .thenReturn(Collections.singletonList(possessedSkill));
        // Search the possessedSkill
        restPossessedSkillMockMvc.perform(get("/api/_search/possessed-skills?query=id:" + possessedSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(possessedSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].topSkill").value(hasItem(DEFAULT_TOP_SKILL.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PossessedSkill.class);
        PossessedSkill possessedSkill1 = new PossessedSkill();
        possessedSkill1.setId(1L);
        PossessedSkill possessedSkill2 = new PossessedSkill();
        possessedSkill2.setId(possessedSkill1.getId());
        assertThat(possessedSkill1).isEqualTo(possessedSkill2);
        possessedSkill2.setId(2L);
        assertThat(possessedSkill1).isNotEqualTo(possessedSkill2);
        possessedSkill1.setId(null);
        assertThat(possessedSkill1).isNotEqualTo(possessedSkill2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PossessedSkillDTO.class);
        PossessedSkillDTO possessedSkillDTO1 = new PossessedSkillDTO();
        possessedSkillDTO1.setId(1L);
        PossessedSkillDTO possessedSkillDTO2 = new PossessedSkillDTO();
        assertThat(possessedSkillDTO1).isNotEqualTo(possessedSkillDTO2);
        possessedSkillDTO2.setId(possessedSkillDTO1.getId());
        assertThat(possessedSkillDTO1).isEqualTo(possessedSkillDTO2);
        possessedSkillDTO2.setId(2L);
        assertThat(possessedSkillDTO1).isNotEqualTo(possessedSkillDTO2);
        possessedSkillDTO1.setId(null);
        assertThat(possessedSkillDTO1).isNotEqualTo(possessedSkillDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(possessedSkillMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(possessedSkillMapper.fromId(null)).isNull();
    }
}
