package com.hc.mymentor.web.rest;

import com.hc.mymentor.MymentorApp;
import com.hc.mymentor.domain.Mentee;
import com.hc.mymentor.repository.MenteeRepository;
import com.hc.mymentor.repository.search.MenteeSearchRepository;
import com.hc.mymentor.service.MenteeService;
import com.hc.mymentor.service.dto.MenteeDTO;
import com.hc.mymentor.service.mapper.MenteeMapper;
import com.hc.mymentor.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
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
 * Integration tests for the {@link MenteeResource} REST controller.
 */
@SpringBootTest(classes = MymentorApp.class)
public class MenteeResourceIT {

    private static final String DEFAULT_MOBILE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_LANDLINE = "AAAAAAAAAA";
    private static final String UPDATED_LANDLINE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PROFILE_VISIBLE_IN_INTERNET = false;
    private static final Boolean UPDATED_PROFILE_VISIBLE_IN_INTERNET = true;

    private static final String DEFAULT_OWN_WEBSITE_URL = "AAAAAAAAAA";
    private static final String UPDATED_OWN_WEBSITE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_XING_PROFILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_XING_PROFILE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LINKED_IN_PROFILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_LINKED_IN_PROFILE_URL = "BBBBBBBBBB";

    @Autowired
    private MenteeRepository menteeRepository;

    @Mock
    private MenteeRepository menteeRepositoryMock;

    @Autowired
    private MenteeMapper menteeMapper;

    @Mock
    private MenteeService menteeServiceMock;

    @Autowired
    private MenteeService menteeService;

    /**
     * This repository is mocked in the com.hc.mymentor.repository.search test package.
     *
     * @see com.hc.mymentor.repository.search.MenteeSearchRepositoryMockConfiguration
     */
    @Autowired
    private MenteeSearchRepository mockMenteeSearchRepository;

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

    private MockMvc restMenteeMockMvc;

    private Mentee mentee;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MenteeResource menteeResource = new MenteeResource(menteeService);
        this.restMenteeMockMvc = MockMvcBuilders.standaloneSetup(menteeResource)
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
    public static Mentee createEntity(EntityManager em) {
        Mentee mentee = new Mentee()
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .landline(DEFAULT_LANDLINE)
            .profileVisibleInInternet(DEFAULT_PROFILE_VISIBLE_IN_INTERNET)
            .ownWebsiteUrl(DEFAULT_OWN_WEBSITE_URL)
            .xingProfileUrl(DEFAULT_XING_PROFILE_URL)
            .linkedInProfileUrl(DEFAULT_LINKED_IN_PROFILE_URL);
        return mentee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mentee createUpdatedEntity(EntityManager em) {
        Mentee mentee = new Mentee()
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .landline(UPDATED_LANDLINE)
            .profileVisibleInInternet(UPDATED_PROFILE_VISIBLE_IN_INTERNET)
            .ownWebsiteUrl(UPDATED_OWN_WEBSITE_URL)
            .xingProfileUrl(UPDATED_XING_PROFILE_URL)
            .linkedInProfileUrl(UPDATED_LINKED_IN_PROFILE_URL);
        return mentee;
    }

    @BeforeEach
    public void initTest() {
        mentee = createEntity(em);
    }

    @Test
    @Transactional
    public void createMentee() throws Exception {
        int databaseSizeBeforeCreate = menteeRepository.findAll().size();

        // Create the Mentee
        MenteeDTO menteeDTO = menteeMapper.toDto(mentee);
        restMenteeMockMvc.perform(post("/api/mentees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menteeDTO)))
            .andExpect(status().isCreated());

        // Validate the Mentee in the database
        List<Mentee> menteeList = menteeRepository.findAll();
        assertThat(menteeList).hasSize(databaseSizeBeforeCreate + 1);
        Mentee testMentee = menteeList.get(menteeList.size() - 1);
        assertThat(testMentee.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testMentee.getLandline()).isEqualTo(DEFAULT_LANDLINE);
        assertThat(testMentee.isProfileVisibleInInternet()).isEqualTo(DEFAULT_PROFILE_VISIBLE_IN_INTERNET);
        assertThat(testMentee.getOwnWebsiteUrl()).isEqualTo(DEFAULT_OWN_WEBSITE_URL);
        assertThat(testMentee.getXingProfileUrl()).isEqualTo(DEFAULT_XING_PROFILE_URL);
        assertThat(testMentee.getLinkedInProfileUrl()).isEqualTo(DEFAULT_LINKED_IN_PROFILE_URL);

        // Validate the Mentee in Elasticsearch
        verify(mockMenteeSearchRepository, times(1)).save(testMentee);
    }

    @Test
    @Transactional
    public void createMenteeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = menteeRepository.findAll().size();

        // Create the Mentee with an existing ID
        mentee.setId(1L);
        MenteeDTO menteeDTO = menteeMapper.toDto(mentee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenteeMockMvc.perform(post("/api/mentees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menteeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mentee in the database
        List<Mentee> menteeList = menteeRepository.findAll();
        assertThat(menteeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Mentee in Elasticsearch
        verify(mockMenteeSearchRepository, times(0)).save(mentee);
    }


    @Test
    @Transactional
    public void getAllMentees() throws Exception {
        // Initialize the database
        menteeRepository.saveAndFlush(mentee);

        // Get all the menteeList
        restMenteeMockMvc.perform(get("/api/mentees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mentee.getId().intValue())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE)))
            .andExpect(jsonPath("$.[*].landline").value(hasItem(DEFAULT_LANDLINE)))
            .andExpect(jsonPath("$.[*].profileVisibleInInternet").value(hasItem(DEFAULT_PROFILE_VISIBLE_IN_INTERNET.booleanValue())))
            .andExpect(jsonPath("$.[*].ownWebsiteUrl").value(hasItem(DEFAULT_OWN_WEBSITE_URL)))
            .andExpect(jsonPath("$.[*].xingProfileUrl").value(hasItem(DEFAULT_XING_PROFILE_URL)))
            .andExpect(jsonPath("$.[*].linkedInProfileUrl").value(hasItem(DEFAULT_LINKED_IN_PROFILE_URL)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMenteesWithEagerRelationshipsIsEnabled() throws Exception {
        MenteeResource menteeResource = new MenteeResource(menteeServiceMock);
        when(menteeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restMenteeMockMvc = MockMvcBuilders.standaloneSetup(menteeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMenteeMockMvc.perform(get("/api/mentees?eagerload=true"))
        .andExpect(status().isOk());

        verify(menteeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMenteesWithEagerRelationshipsIsNotEnabled() throws Exception {
        MenteeResource menteeResource = new MenteeResource(menteeServiceMock);
            when(menteeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restMenteeMockMvc = MockMvcBuilders.standaloneSetup(menteeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMenteeMockMvc.perform(get("/api/mentees?eagerload=true"))
        .andExpect(status().isOk());

            verify(menteeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMentee() throws Exception {
        // Initialize the database
        menteeRepository.saveAndFlush(mentee);

        // Get the mentee
        restMenteeMockMvc.perform(get("/api/mentees/{id}", mentee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mentee.getId().intValue()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE))
            .andExpect(jsonPath("$.landline").value(DEFAULT_LANDLINE))
            .andExpect(jsonPath("$.profileVisibleInInternet").value(DEFAULT_PROFILE_VISIBLE_IN_INTERNET.booleanValue()))
            .andExpect(jsonPath("$.ownWebsiteUrl").value(DEFAULT_OWN_WEBSITE_URL))
            .andExpect(jsonPath("$.xingProfileUrl").value(DEFAULT_XING_PROFILE_URL))
            .andExpect(jsonPath("$.linkedInProfileUrl").value(DEFAULT_LINKED_IN_PROFILE_URL));
    }

    @Test
    @Transactional
    public void getNonExistingMentee() throws Exception {
        // Get the mentee
        restMenteeMockMvc.perform(get("/api/mentees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMentee() throws Exception {
        // Initialize the database
        menteeRepository.saveAndFlush(mentee);

        int databaseSizeBeforeUpdate = menteeRepository.findAll().size();

        // Update the mentee
        Mentee updatedMentee = menteeRepository.findById(mentee.getId()).get();
        // Disconnect from session so that the updates on updatedMentee are not directly saved in db
        em.detach(updatedMentee);
        updatedMentee
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .landline(UPDATED_LANDLINE)
            .profileVisibleInInternet(UPDATED_PROFILE_VISIBLE_IN_INTERNET)
            .ownWebsiteUrl(UPDATED_OWN_WEBSITE_URL)
            .xingProfileUrl(UPDATED_XING_PROFILE_URL)
            .linkedInProfileUrl(UPDATED_LINKED_IN_PROFILE_URL);
        MenteeDTO menteeDTO = menteeMapper.toDto(updatedMentee);

        restMenteeMockMvc.perform(put("/api/mentees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menteeDTO)))
            .andExpect(status().isOk());

        // Validate the Mentee in the database
        List<Mentee> menteeList = menteeRepository.findAll();
        assertThat(menteeList).hasSize(databaseSizeBeforeUpdate);
        Mentee testMentee = menteeList.get(menteeList.size() - 1);
        assertThat(testMentee.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testMentee.getLandline()).isEqualTo(UPDATED_LANDLINE);
        assertThat(testMentee.isProfileVisibleInInternet()).isEqualTo(UPDATED_PROFILE_VISIBLE_IN_INTERNET);
        assertThat(testMentee.getOwnWebsiteUrl()).isEqualTo(UPDATED_OWN_WEBSITE_URL);
        assertThat(testMentee.getXingProfileUrl()).isEqualTo(UPDATED_XING_PROFILE_URL);
        assertThat(testMentee.getLinkedInProfileUrl()).isEqualTo(UPDATED_LINKED_IN_PROFILE_URL);

        // Validate the Mentee in Elasticsearch
        verify(mockMenteeSearchRepository, times(1)).save(testMentee);
    }

    @Test
    @Transactional
    public void updateNonExistingMentee() throws Exception {
        int databaseSizeBeforeUpdate = menteeRepository.findAll().size();

        // Create the Mentee
        MenteeDTO menteeDTO = menteeMapper.toDto(mentee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenteeMockMvc.perform(put("/api/mentees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menteeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mentee in the database
        List<Mentee> menteeList = menteeRepository.findAll();
        assertThat(menteeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Mentee in Elasticsearch
        verify(mockMenteeSearchRepository, times(0)).save(mentee);
    }

    @Test
    @Transactional
    public void deleteMentee() throws Exception {
        // Initialize the database
        menteeRepository.saveAndFlush(mentee);

        int databaseSizeBeforeDelete = menteeRepository.findAll().size();

        // Delete the mentee
        restMenteeMockMvc.perform(delete("/api/mentees/{id}", mentee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mentee> menteeList = menteeRepository.findAll();
        assertThat(menteeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Mentee in Elasticsearch
        verify(mockMenteeSearchRepository, times(1)).deleteById(mentee.getId());
    }

    @Test
    @Transactional
    public void searchMentee() throws Exception {
        // Initialize the database
        menteeRepository.saveAndFlush(mentee);
        when(mockMenteeSearchRepository.search(queryStringQuery("id:" + mentee.getId())))
            .thenReturn(Collections.singletonList(mentee));
        // Search the mentee
        restMenteeMockMvc.perform(get("/api/_search/mentees?query=id:" + mentee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mentee.getId().intValue())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE)))
            .andExpect(jsonPath("$.[*].landline").value(hasItem(DEFAULT_LANDLINE)))
            .andExpect(jsonPath("$.[*].profileVisibleInInternet").value(hasItem(DEFAULT_PROFILE_VISIBLE_IN_INTERNET.booleanValue())))
            .andExpect(jsonPath("$.[*].ownWebsiteUrl").value(hasItem(DEFAULT_OWN_WEBSITE_URL)))
            .andExpect(jsonPath("$.[*].xingProfileUrl").value(hasItem(DEFAULT_XING_PROFILE_URL)))
            .andExpect(jsonPath("$.[*].linkedInProfileUrl").value(hasItem(DEFAULT_LINKED_IN_PROFILE_URL)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mentee.class);
        Mentee mentee1 = new Mentee();
        mentee1.setId(1L);
        Mentee mentee2 = new Mentee();
        mentee2.setId(mentee1.getId());
        assertThat(mentee1).isEqualTo(mentee2);
        mentee2.setId(2L);
        assertThat(mentee1).isNotEqualTo(mentee2);
        mentee1.setId(null);
        assertThat(mentee1).isNotEqualTo(mentee2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenteeDTO.class);
        MenteeDTO menteeDTO1 = new MenteeDTO();
        menteeDTO1.setId(1L);
        MenteeDTO menteeDTO2 = new MenteeDTO();
        assertThat(menteeDTO1).isNotEqualTo(menteeDTO2);
        menteeDTO2.setId(menteeDTO1.getId());
        assertThat(menteeDTO1).isEqualTo(menteeDTO2);
        menteeDTO2.setId(2L);
        assertThat(menteeDTO1).isNotEqualTo(menteeDTO2);
        menteeDTO1.setId(null);
        assertThat(menteeDTO1).isNotEqualTo(menteeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(menteeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(menteeMapper.fromId(null)).isNull();
    }
}
