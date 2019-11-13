package com.hc.mymentor.web.rest;

import com.hc.mymentor.MymentorApp;
import com.hc.mymentor.domain.CareerStone;
import com.hc.mymentor.repository.CareerStoneRepository;
import com.hc.mymentor.repository.search.CareerStoneSearchRepository;
import com.hc.mymentor.service.CareerStoneService;
import com.hc.mymentor.service.dto.CareerStoneDTO;
import com.hc.mymentor.service.mapper.CareerStoneMapper;
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
 * Integration tests for the {@link CareerStoneResource} REST controller.
 */
@SpringBootTest(classes = MymentorApp.class)
public class CareerStoneResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_STILL_WORKING_HERE = false;
    private static final Boolean UPDATED_STILL_WORKING_HERE = true;

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CareerStoneRepository careerStoneRepository;

    @Autowired
    private CareerStoneMapper careerStoneMapper;

    @Autowired
    private CareerStoneService careerStoneService;

    /**
     * This repository is mocked in the com.hc.mymentor.repository.search test package.
     *
     * @see com.hc.mymentor.repository.search.CareerStoneSearchRepositoryMockConfiguration
     */
    @Autowired
    private CareerStoneSearchRepository mockCareerStoneSearchRepository;

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

    private MockMvc restCareerStoneMockMvc;

    private CareerStone careerStone;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CareerStoneResource careerStoneResource = new CareerStoneResource(careerStoneService);
        this.restCareerStoneMockMvc = MockMvcBuilders.standaloneSetup(careerStoneResource)
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
    public static CareerStone createEntity(EntityManager em) {
        CareerStone careerStone = new CareerStone()
            .title(DEFAULT_TITLE)
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .stillWorkingHere(DEFAULT_STILL_WORKING_HERE)
            .location(DEFAULT_LOCATION)
            .description(DEFAULT_DESCRIPTION);
        return careerStone;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CareerStone createUpdatedEntity(EntityManager em) {
        CareerStone careerStone = new CareerStone()
            .title(UPDATED_TITLE)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .stillWorkingHere(UPDATED_STILL_WORKING_HERE)
            .location(UPDATED_LOCATION)
            .description(UPDATED_DESCRIPTION);
        return careerStone;
    }

    @BeforeEach
    public void initTest() {
        careerStone = createEntity(em);
    }

    @Test
    @Transactional
    public void createCareerStone() throws Exception {
        int databaseSizeBeforeCreate = careerStoneRepository.findAll().size();

        // Create the CareerStone
        CareerStoneDTO careerStoneDTO = careerStoneMapper.toDto(careerStone);
        restCareerStoneMockMvc.perform(post("/api/career-stones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerStoneDTO)))
            .andExpect(status().isCreated());

        // Validate the CareerStone in the database
        List<CareerStone> careerStoneList = careerStoneRepository.findAll();
        assertThat(careerStoneList).hasSize(databaseSizeBeforeCreate + 1);
        CareerStone testCareerStone = careerStoneList.get(careerStoneList.size() - 1);
        assertThat(testCareerStone.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCareerStone.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testCareerStone.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testCareerStone.isStillWorkingHere()).isEqualTo(DEFAULT_STILL_WORKING_HERE);
        assertThat(testCareerStone.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testCareerStone.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the CareerStone in Elasticsearch
        verify(mockCareerStoneSearchRepository, times(1)).save(testCareerStone);
    }

    @Test
    @Transactional
    public void createCareerStoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = careerStoneRepository.findAll().size();

        // Create the CareerStone with an existing ID
        careerStone.setId(1L);
        CareerStoneDTO careerStoneDTO = careerStoneMapper.toDto(careerStone);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCareerStoneMockMvc.perform(post("/api/career-stones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerStoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CareerStone in the database
        List<CareerStone> careerStoneList = careerStoneRepository.findAll();
        assertThat(careerStoneList).hasSize(databaseSizeBeforeCreate);

        // Validate the CareerStone in Elasticsearch
        verify(mockCareerStoneSearchRepository, times(0)).save(careerStone);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = careerStoneRepository.findAll().size();
        // set the field null
        careerStone.setTitle(null);

        // Create the CareerStone, which fails.
        CareerStoneDTO careerStoneDTO = careerStoneMapper.toDto(careerStone);

        restCareerStoneMockMvc.perform(post("/api/career-stones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerStoneDTO)))
            .andExpect(status().isBadRequest());

        List<CareerStone> careerStoneList = careerStoneRepository.findAll();
        assertThat(careerStoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFromDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = careerStoneRepository.findAll().size();
        // set the field null
        careerStone.setFromDate(null);

        // Create the CareerStone, which fails.
        CareerStoneDTO careerStoneDTO = careerStoneMapper.toDto(careerStone);

        restCareerStoneMockMvc.perform(post("/api/career-stones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerStoneDTO)))
            .andExpect(status().isBadRequest());

        List<CareerStone> careerStoneList = careerStoneRepository.findAll();
        assertThat(careerStoneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCareerStones() throws Exception {
        // Initialize the database
        careerStoneRepository.saveAndFlush(careerStone);

        // Get all the careerStoneList
        restCareerStoneMockMvc.perform(get("/api/career-stones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(careerStone.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].stillWorkingHere").value(hasItem(DEFAULT_STILL_WORKING_HERE.booleanValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getCareerStone() throws Exception {
        // Initialize the database
        careerStoneRepository.saveAndFlush(careerStone);

        // Get the careerStone
        restCareerStoneMockMvc.perform(get("/api/career-stones/{id}", careerStone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(careerStone.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.stillWorkingHere").value(DEFAULT_STILL_WORKING_HERE.booleanValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingCareerStone() throws Exception {
        // Get the careerStone
        restCareerStoneMockMvc.perform(get("/api/career-stones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCareerStone() throws Exception {
        // Initialize the database
        careerStoneRepository.saveAndFlush(careerStone);

        int databaseSizeBeforeUpdate = careerStoneRepository.findAll().size();

        // Update the careerStone
        CareerStone updatedCareerStone = careerStoneRepository.findById(careerStone.getId()).get();
        // Disconnect from session so that the updates on updatedCareerStone are not directly saved in db
        em.detach(updatedCareerStone);
        updatedCareerStone
            .title(UPDATED_TITLE)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .stillWorkingHere(UPDATED_STILL_WORKING_HERE)
            .location(UPDATED_LOCATION)
            .description(UPDATED_DESCRIPTION);
        CareerStoneDTO careerStoneDTO = careerStoneMapper.toDto(updatedCareerStone);

        restCareerStoneMockMvc.perform(put("/api/career-stones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerStoneDTO)))
            .andExpect(status().isOk());

        // Validate the CareerStone in the database
        List<CareerStone> careerStoneList = careerStoneRepository.findAll();
        assertThat(careerStoneList).hasSize(databaseSizeBeforeUpdate);
        CareerStone testCareerStone = careerStoneList.get(careerStoneList.size() - 1);
        assertThat(testCareerStone.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCareerStone.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testCareerStone.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testCareerStone.isStillWorkingHere()).isEqualTo(UPDATED_STILL_WORKING_HERE);
        assertThat(testCareerStone.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCareerStone.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the CareerStone in Elasticsearch
        verify(mockCareerStoneSearchRepository, times(1)).save(testCareerStone);
    }

    @Test
    @Transactional
    public void updateNonExistingCareerStone() throws Exception {
        int databaseSizeBeforeUpdate = careerStoneRepository.findAll().size();

        // Create the CareerStone
        CareerStoneDTO careerStoneDTO = careerStoneMapper.toDto(careerStone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCareerStoneMockMvc.perform(put("/api/career-stones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerStoneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CareerStone in the database
        List<CareerStone> careerStoneList = careerStoneRepository.findAll();
        assertThat(careerStoneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CareerStone in Elasticsearch
        verify(mockCareerStoneSearchRepository, times(0)).save(careerStone);
    }

    @Test
    @Transactional
    public void deleteCareerStone() throws Exception {
        // Initialize the database
        careerStoneRepository.saveAndFlush(careerStone);

        int databaseSizeBeforeDelete = careerStoneRepository.findAll().size();

        // Delete the careerStone
        restCareerStoneMockMvc.perform(delete("/api/career-stones/{id}", careerStone.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CareerStone> careerStoneList = careerStoneRepository.findAll();
        assertThat(careerStoneList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CareerStone in Elasticsearch
        verify(mockCareerStoneSearchRepository, times(1)).deleteById(careerStone.getId());
    }

    @Test
    @Transactional
    public void searchCareerStone() throws Exception {
        // Initialize the database
        careerStoneRepository.saveAndFlush(careerStone);
        when(mockCareerStoneSearchRepository.search(queryStringQuery("id:" + careerStone.getId())))
            .thenReturn(Collections.singletonList(careerStone));
        // Search the careerStone
        restCareerStoneMockMvc.perform(get("/api/_search/career-stones?query=id:" + careerStone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(careerStone.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].stillWorkingHere").value(hasItem(DEFAULT_STILL_WORKING_HERE.booleanValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CareerStone.class);
        CareerStone careerStone1 = new CareerStone();
        careerStone1.setId(1L);
        CareerStone careerStone2 = new CareerStone();
        careerStone2.setId(careerStone1.getId());
        assertThat(careerStone1).isEqualTo(careerStone2);
        careerStone2.setId(2L);
        assertThat(careerStone1).isNotEqualTo(careerStone2);
        careerStone1.setId(null);
        assertThat(careerStone1).isNotEqualTo(careerStone2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CareerStoneDTO.class);
        CareerStoneDTO careerStoneDTO1 = new CareerStoneDTO();
        careerStoneDTO1.setId(1L);
        CareerStoneDTO careerStoneDTO2 = new CareerStoneDTO();
        assertThat(careerStoneDTO1).isNotEqualTo(careerStoneDTO2);
        careerStoneDTO2.setId(careerStoneDTO1.getId());
        assertThat(careerStoneDTO1).isEqualTo(careerStoneDTO2);
        careerStoneDTO2.setId(2L);
        assertThat(careerStoneDTO1).isNotEqualTo(careerStoneDTO2);
        careerStoneDTO1.setId(null);
        assertThat(careerStoneDTO1).isNotEqualTo(careerStoneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(careerStoneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(careerStoneMapper.fromId(null)).isNull();
    }
}
