package com.hc.mymentor.web.rest;

import com.hc.mymentor.MymentorApp;
import com.hc.mymentor.domain.EduInstitution;
import com.hc.mymentor.repository.EduInstitutionRepository;
import com.hc.mymentor.repository.search.EduInstitutionSearchRepository;
import com.hc.mymentor.service.EduInstitutionService;
import com.hc.mymentor.service.dto.EduInstitutionDTO;
import com.hc.mymentor.service.mapper.EduInstitutionMapper;
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
 * Integration tests for the {@link EduInstitutionResource} REST controller.
 */
@SpringBootTest(classes = MymentorApp.class)
public class EduInstitutionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private EduInstitutionRepository eduInstitutionRepository;

    @Autowired
    private EduInstitutionMapper eduInstitutionMapper;

    @Autowired
    private EduInstitutionService eduInstitutionService;

    /**
     * This repository is mocked in the com.hc.mymentor.repository.search test package.
     *
     * @see com.hc.mymentor.repository.search.EduInstitutionSearchRepositoryMockConfiguration
     */
    @Autowired
    private EduInstitutionSearchRepository mockEduInstitutionSearchRepository;

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

    private MockMvc restEduInstitutionMockMvc;

    private EduInstitution eduInstitution;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EduInstitutionResource eduInstitutionResource = new EduInstitutionResource(eduInstitutionService);
        this.restEduInstitutionMockMvc = MockMvcBuilders.standaloneSetup(eduInstitutionResource)
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
    public static EduInstitution createEntity(EntityManager em) {
        EduInstitution eduInstitution = new EduInstitution()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .url(DEFAULT_URL);
        return eduInstitution;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EduInstitution createUpdatedEntity(EntityManager em) {
        EduInstitution eduInstitution = new EduInstitution()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .url(UPDATED_URL);
        return eduInstitution;
    }

    @BeforeEach
    public void initTest() {
        eduInstitution = createEntity(em);
    }

    @Test
    @Transactional
    public void createEduInstitution() throws Exception {
        int databaseSizeBeforeCreate = eduInstitutionRepository.findAll().size();

        // Create the EduInstitution
        EduInstitutionDTO eduInstitutionDTO = eduInstitutionMapper.toDto(eduInstitution);
        restEduInstitutionMockMvc.perform(post("/api/edu-institutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eduInstitutionDTO)))
            .andExpect(status().isCreated());

        // Validate the EduInstitution in the database
        List<EduInstitution> eduInstitutionList = eduInstitutionRepository.findAll();
        assertThat(eduInstitutionList).hasSize(databaseSizeBeforeCreate + 1);
        EduInstitution testEduInstitution = eduInstitutionList.get(eduInstitutionList.size() - 1);
        assertThat(testEduInstitution.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEduInstitution.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEduInstitution.getUrl()).isEqualTo(DEFAULT_URL);

        // Validate the EduInstitution in Elasticsearch
        verify(mockEduInstitutionSearchRepository, times(1)).save(testEduInstitution);
    }

    @Test
    @Transactional
    public void createEduInstitutionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eduInstitutionRepository.findAll().size();

        // Create the EduInstitution with an existing ID
        eduInstitution.setId(1L);
        EduInstitutionDTO eduInstitutionDTO = eduInstitutionMapper.toDto(eduInstitution);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEduInstitutionMockMvc.perform(post("/api/edu-institutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eduInstitutionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EduInstitution in the database
        List<EduInstitution> eduInstitutionList = eduInstitutionRepository.findAll();
        assertThat(eduInstitutionList).hasSize(databaseSizeBeforeCreate);

        // Validate the EduInstitution in Elasticsearch
        verify(mockEduInstitutionSearchRepository, times(0)).save(eduInstitution);
    }


    @Test
    @Transactional
    public void getAllEduInstitutions() throws Exception {
        // Initialize the database
        eduInstitutionRepository.saveAndFlush(eduInstitution);

        // Get all the eduInstitutionList
        restEduInstitutionMockMvc.perform(get("/api/edu-institutions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eduInstitution.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }
    
    @Test
    @Transactional
    public void getEduInstitution() throws Exception {
        // Initialize the database
        eduInstitutionRepository.saveAndFlush(eduInstitution);

        // Get the eduInstitution
        restEduInstitutionMockMvc.perform(get("/api/edu-institutions/{id}", eduInstitution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eduInstitution.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }

    @Test
    @Transactional
    public void getNonExistingEduInstitution() throws Exception {
        // Get the eduInstitution
        restEduInstitutionMockMvc.perform(get("/api/edu-institutions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEduInstitution() throws Exception {
        // Initialize the database
        eduInstitutionRepository.saveAndFlush(eduInstitution);

        int databaseSizeBeforeUpdate = eduInstitutionRepository.findAll().size();

        // Update the eduInstitution
        EduInstitution updatedEduInstitution = eduInstitutionRepository.findById(eduInstitution.getId()).get();
        // Disconnect from session so that the updates on updatedEduInstitution are not directly saved in db
        em.detach(updatedEduInstitution);
        updatedEduInstitution
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .url(UPDATED_URL);
        EduInstitutionDTO eduInstitutionDTO = eduInstitutionMapper.toDto(updatedEduInstitution);

        restEduInstitutionMockMvc.perform(put("/api/edu-institutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eduInstitutionDTO)))
            .andExpect(status().isOk());

        // Validate the EduInstitution in the database
        List<EduInstitution> eduInstitutionList = eduInstitutionRepository.findAll();
        assertThat(eduInstitutionList).hasSize(databaseSizeBeforeUpdate);
        EduInstitution testEduInstitution = eduInstitutionList.get(eduInstitutionList.size() - 1);
        assertThat(testEduInstitution.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEduInstitution.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEduInstitution.getUrl()).isEqualTo(UPDATED_URL);

        // Validate the EduInstitution in Elasticsearch
        verify(mockEduInstitutionSearchRepository, times(1)).save(testEduInstitution);
    }

    @Test
    @Transactional
    public void updateNonExistingEduInstitution() throws Exception {
        int databaseSizeBeforeUpdate = eduInstitutionRepository.findAll().size();

        // Create the EduInstitution
        EduInstitutionDTO eduInstitutionDTO = eduInstitutionMapper.toDto(eduInstitution);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEduInstitutionMockMvc.perform(put("/api/edu-institutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eduInstitutionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EduInstitution in the database
        List<EduInstitution> eduInstitutionList = eduInstitutionRepository.findAll();
        assertThat(eduInstitutionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EduInstitution in Elasticsearch
        verify(mockEduInstitutionSearchRepository, times(0)).save(eduInstitution);
    }

    @Test
    @Transactional
    public void deleteEduInstitution() throws Exception {
        // Initialize the database
        eduInstitutionRepository.saveAndFlush(eduInstitution);

        int databaseSizeBeforeDelete = eduInstitutionRepository.findAll().size();

        // Delete the eduInstitution
        restEduInstitutionMockMvc.perform(delete("/api/edu-institutions/{id}", eduInstitution.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EduInstitution> eduInstitutionList = eduInstitutionRepository.findAll();
        assertThat(eduInstitutionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EduInstitution in Elasticsearch
        verify(mockEduInstitutionSearchRepository, times(1)).deleteById(eduInstitution.getId());
    }

    @Test
    @Transactional
    public void searchEduInstitution() throws Exception {
        // Initialize the database
        eduInstitutionRepository.saveAndFlush(eduInstitution);
        when(mockEduInstitutionSearchRepository.search(queryStringQuery("id:" + eduInstitution.getId())))
            .thenReturn(Collections.singletonList(eduInstitution));
        // Search the eduInstitution
        restEduInstitutionMockMvc.perform(get("/api/_search/edu-institutions?query=id:" + eduInstitution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eduInstitution.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EduInstitution.class);
        EduInstitution eduInstitution1 = new EduInstitution();
        eduInstitution1.setId(1L);
        EduInstitution eduInstitution2 = new EduInstitution();
        eduInstitution2.setId(eduInstitution1.getId());
        assertThat(eduInstitution1).isEqualTo(eduInstitution2);
        eduInstitution2.setId(2L);
        assertThat(eduInstitution1).isNotEqualTo(eduInstitution2);
        eduInstitution1.setId(null);
        assertThat(eduInstitution1).isNotEqualTo(eduInstitution2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EduInstitutionDTO.class);
        EduInstitutionDTO eduInstitutionDTO1 = new EduInstitutionDTO();
        eduInstitutionDTO1.setId(1L);
        EduInstitutionDTO eduInstitutionDTO2 = new EduInstitutionDTO();
        assertThat(eduInstitutionDTO1).isNotEqualTo(eduInstitutionDTO2);
        eduInstitutionDTO2.setId(eduInstitutionDTO1.getId());
        assertThat(eduInstitutionDTO1).isEqualTo(eduInstitutionDTO2);
        eduInstitutionDTO2.setId(2L);
        assertThat(eduInstitutionDTO1).isNotEqualTo(eduInstitutionDTO2);
        eduInstitutionDTO1.setId(null);
        assertThat(eduInstitutionDTO1).isNotEqualTo(eduInstitutionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eduInstitutionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eduInstitutionMapper.fromId(null)).isNull();
    }
}
