package com.hc.mymentor.web.rest;

import com.hc.mymentor.MymentorApp;
import com.hc.mymentor.domain.EduStone;
import com.hc.mymentor.repository.EduStoneRepository;
import com.hc.mymentor.repository.search.EduStoneSearchRepository;
import com.hc.mymentor.service.EduStoneService;
import com.hc.mymentor.service.dto.EduStoneDTO;
import com.hc.mymentor.service.mapper.EduStoneMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link EduStoneResource} REST controller.
 */
@SpringBootTest(classes = MymentorApp.class)
public class EduStoneResourceIT {

    private static final String DEFAULT_DEGREE = "AAAAAAAAAA";
    private static final String UPDATED_DEGREE = "BBBBBBBBBB";

    private static final String DEFAULT_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_GRADE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITIES = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITIES = "BBBBBBBBBB";

    @Autowired
    private EduStoneRepository eduStoneRepository;

    @Autowired
    private EduStoneMapper eduStoneMapper;

    @Autowired
    private EduStoneService eduStoneService;

    /**
     * This repository is mocked in the com.hc.mymentor.repository.search test package.
     *
     * @see com.hc.mymentor.repository.search.EduStoneSearchRepositoryMockConfiguration
     */
    @Autowired
    private EduStoneSearchRepository mockEduStoneSearchRepository;

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

    private MockMvc restEduStoneMockMvc;

    private EduStone eduStone;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EduStoneResource eduStoneResource = new EduStoneResource(eduStoneService);
        this.restEduStoneMockMvc = MockMvcBuilders.standaloneSetup(eduStoneResource)
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
    public static EduStone createEntity(EntityManager em) {
        EduStone eduStone = new EduStone()
            .degree(DEFAULT_DEGREE)
            .grade(DEFAULT_GRADE)
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .description(DEFAULT_DESCRIPTION)
            .activities(DEFAULT_ACTIVITIES);
        return eduStone;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EduStone createUpdatedEntity(EntityManager em) {
        EduStone eduStone = new EduStone()
            .degree(UPDATED_DEGREE)
            .grade(UPDATED_GRADE)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .description(UPDATED_DESCRIPTION)
            .activities(UPDATED_ACTIVITIES);
        return eduStone;
    }

    @BeforeEach
    public void initTest() {
        eduStone = createEntity(em);
    }

    @Test
    @Transactional
    public void createEduStone() throws Exception {
        int databaseSizeBeforeCreate = eduStoneRepository.findAll().size();

        // Create the EduStone
        EduStoneDTO eduStoneDTO = eduStoneMapper.toDto(eduStone);
        restEduStoneMockMvc.perform(post("/api/edu-stones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eduStoneDTO)))
            .andExpect(status().isCreated());

        // Validate the EduStone in the database
        List<EduStone> eduStoneList = eduStoneRepository.findAll();
        assertThat(eduStoneList).hasSize(databaseSizeBeforeCreate + 1);
        EduStone testEduStone = eduStoneList.get(eduStoneList.size() - 1);
        assertThat(testEduStone.getDegree()).isEqualTo(DEFAULT_DEGREE);
        assertThat(testEduStone.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEduStone.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testEduStone.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testEduStone.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEduStone.getActivities()).isEqualTo(DEFAULT_ACTIVITIES);

        // Validate the EduStone in Elasticsearch
        verify(mockEduStoneSearchRepository, times(1)).save(testEduStone);
    }

    @Test
    @Transactional
    public void createEduStoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eduStoneRepository.findAll().size();

        // Create the EduStone with an existing ID
        eduStone.setId(1L);
        EduStoneDTO eduStoneDTO = eduStoneMapper.toDto(eduStone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEduStoneMockMvc.perform(post("/api/edu-stones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eduStoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EduStone in the database
        List<EduStone> eduStoneList = eduStoneRepository.findAll();
        assertThat(eduStoneList).hasSize(databaseSizeBeforeCreate);

        // Validate the EduStone in Elasticsearch
        verify(mockEduStoneSearchRepository, times(0)).save(eduStone);
    }


    @Test
    @Transactional
    public void checkDegreeIsRequired() throws Exception {
        int databaseSizeBeforeTest = eduStoneRepository.findAll().size();
        // set the field null
        eduStone.setDegree(null);

        // Create the EduStone, which fails.
        EduStoneDTO eduStoneDTO = eduStoneMapper.toDto(eduStone);

        restEduStoneMockMvc.perform(post("/api/edu-stones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eduStoneDTO)))
            .andExpect(status().isBadRequest());

        List<EduStone> eduStoneList = eduStoneRepository.findAll();
        assertThat(eduStoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFromDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = eduStoneRepository.findAll().size();
        // set the field null
        eduStone.setFromDate(null);

        // Create the EduStone, which fails.
        EduStoneDTO eduStoneDTO = eduStoneMapper.toDto(eduStone);

        restEduStoneMockMvc.perform(post("/api/edu-stones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eduStoneDTO)))
            .andExpect(status().isBadRequest());

        List<EduStone> eduStoneList = eduStoneRepository.findAll();
        assertThat(eduStoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = eduStoneRepository.findAll().size();
        // set the field null
        eduStone.setToDate(null);

        // Create the EduStone, which fails.
        EduStoneDTO eduStoneDTO = eduStoneMapper.toDto(eduStone);

        restEduStoneMockMvc.perform(post("/api/edu-stones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eduStoneDTO)))
            .andExpect(status().isBadRequest());

        List<EduStone> eduStoneList = eduStoneRepository.findAll();
        assertThat(eduStoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEduStones() throws Exception {
        // Initialize the database
        eduStoneRepository.saveAndFlush(eduStone);

        // Get all the eduStoneList
        restEduStoneMockMvc.perform(get("/api/edu-stones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eduStone.getId().intValue())))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].activities").value(hasItem(DEFAULT_ACTIVITIES)));
    }
    
    @Test
    @Transactional
    public void getEduStone() throws Exception {
        // Initialize the database
        eduStoneRepository.saveAndFlush(eduStone);

        // Get the eduStone
        restEduStoneMockMvc.perform(get("/api/edu-stones/{id}", eduStone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eduStone.getId().intValue()))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.activities").value(DEFAULT_ACTIVITIES));
    }

    @Test
    @Transactional
    public void getNonExistingEduStone() throws Exception {
        // Get the eduStone
        restEduStoneMockMvc.perform(get("/api/edu-stones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEduStone() throws Exception {
        // Initialize the database
        eduStoneRepository.saveAndFlush(eduStone);

        int databaseSizeBeforeUpdate = eduStoneRepository.findAll().size();

        // Update the eduStone
        EduStone updatedEduStone = eduStoneRepository.findById(eduStone.getId()).get();
        // Disconnect from session so that the updates on updatedEduStone are not directly saved in db
        em.detach(updatedEduStone);
        updatedEduStone
            .degree(UPDATED_DEGREE)
            .grade(UPDATED_GRADE)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .description(UPDATED_DESCRIPTION)
            .activities(UPDATED_ACTIVITIES);
        EduStoneDTO eduStoneDTO = eduStoneMapper.toDto(updatedEduStone);

        restEduStoneMockMvc.perform(put("/api/edu-stones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eduStoneDTO)))
            .andExpect(status().isOk());

        // Validate the EduStone in the database
        List<EduStone> eduStoneList = eduStoneRepository.findAll();
        assertThat(eduStoneList).hasSize(databaseSizeBeforeUpdate);
        EduStone testEduStone = eduStoneList.get(eduStoneList.size() - 1);
        assertThat(testEduStone.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testEduStone.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEduStone.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testEduStone.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testEduStone.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEduStone.getActivities()).isEqualTo(UPDATED_ACTIVITIES);

        // Validate the EduStone in Elasticsearch
        verify(mockEduStoneSearchRepository, times(1)).save(testEduStone);
    }

    @Test
    @Transactional
    public void updateNonExistingEduStone() throws Exception {
        int databaseSizeBeforeUpdate = eduStoneRepository.findAll().size();

        // Create the EduStone
        EduStoneDTO eduStoneDTO = eduStoneMapper.toDto(eduStone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEduStoneMockMvc.perform(put("/api/edu-stones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eduStoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EduStone in the database
        List<EduStone> eduStoneList = eduStoneRepository.findAll();
        assertThat(eduStoneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EduStone in Elasticsearch
        verify(mockEduStoneSearchRepository, times(0)).save(eduStone);
    }

    @Test
    @Transactional
    public void deleteEduStone() throws Exception {
        // Initialize the database
        eduStoneRepository.saveAndFlush(eduStone);

        int databaseSizeBeforeDelete = eduStoneRepository.findAll().size();

        // Delete the eduStone
        restEduStoneMockMvc.perform(delete("/api/edu-stones/{id}", eduStone.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EduStone> eduStoneList = eduStoneRepository.findAll();
        assertThat(eduStoneList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EduStone in Elasticsearch
        verify(mockEduStoneSearchRepository, times(1)).deleteById(eduStone.getId());
    }

    @Test
    @Transactional
    public void searchEduStone() throws Exception {
        // Initialize the database
        eduStoneRepository.saveAndFlush(eduStone);
        when(mockEduStoneSearchRepository.search(queryStringQuery("id:" + eduStone.getId())))
            .thenReturn(Collections.singletonList(eduStone));
        // Search the eduStone
        restEduStoneMockMvc.perform(get("/api/_search/edu-stones?query=id:" + eduStone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eduStone.getId().intValue())))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].activities").value(hasItem(DEFAULT_ACTIVITIES)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EduStone.class);
        EduStone eduStone1 = new EduStone();
        eduStone1.setId(1L);
        EduStone eduStone2 = new EduStone();
        eduStone2.setId(eduStone1.getId());
        assertThat(eduStone1).isEqualTo(eduStone2);
        eduStone2.setId(2L);
        assertThat(eduStone1).isNotEqualTo(eduStone2);
        eduStone1.setId(null);
        assertThat(eduStone1).isNotEqualTo(eduStone2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EduStoneDTO.class);
        EduStoneDTO eduStoneDTO1 = new EduStoneDTO();
        eduStoneDTO1.setId(1L);
        EduStoneDTO eduStoneDTO2 = new EduStoneDTO();
        assertThat(eduStoneDTO1).isNotEqualTo(eduStoneDTO2);
        eduStoneDTO2.setId(eduStoneDTO1.getId());
        assertThat(eduStoneDTO1).isEqualTo(eduStoneDTO2);
        eduStoneDTO2.setId(2L);
        assertThat(eduStoneDTO1).isNotEqualTo(eduStoneDTO2);
        eduStoneDTO1.setId(null);
        assertThat(eduStoneDTO1).isNotEqualTo(eduStoneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eduStoneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eduStoneMapper.fromId(null)).isNull();
    }
}
