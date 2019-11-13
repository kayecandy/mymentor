package com.hc.mymentor.web.rest;

import com.hc.mymentor.MymentorApp;
import com.hc.mymentor.domain.Skill;
import com.hc.mymentor.repository.SkillRepository;
import com.hc.mymentor.repository.search.SkillSearchRepository;
import com.hc.mymentor.service.SkillService;
import com.hc.mymentor.service.dto.SkillDTO;
import com.hc.mymentor.service.mapper.SkillMapper;
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

import com.hc.mymentor.domain.enumeration.SkillType;
/**
 * Integration tests for the {@link SkillResource} REST controller.
 */
@SpringBootTest(classes = MymentorApp.class)
public class SkillResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final SkillType DEFAULT_TYPE = SkillType.INDUSTRY_KNOWLEDGE;
    private static final SkillType UPDATED_TYPE = SkillType.TOOLS_TECHNOLOGIES;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SkillMapper skillMapper;

    @Autowired
    private SkillService skillService;

    /**
     * This repository is mocked in the com.hc.mymentor.repository.search test package.
     *
     * @see com.hc.mymentor.repository.search.SkillSearchRepositoryMockConfiguration
     */
    @Autowired
    private SkillSearchRepository mockSkillSearchRepository;

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

    private MockMvc restSkillMockMvc;

    private Skill skill;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkillResource skillResource = new SkillResource(skillService);
        this.restSkillMockMvc = MockMvcBuilders.standaloneSetup(skillResource)
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
    public static Skill createEntity(EntityManager em) {
        Skill skill = new Skill()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return skill;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Skill createUpdatedEntity(EntityManager em) {
        Skill skill = new Skill()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION);
        return skill;
    }

    @BeforeEach
    public void initTest() {
        skill = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkill() throws Exception {
        int databaseSizeBeforeCreate = skillRepository.findAll().size();

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);
        restSkillMockMvc.perform(post("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isCreated());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeCreate + 1);
        Skill testSkill = skillList.get(skillList.size() - 1);
        assertThat(testSkill.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSkill.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSkill.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Skill in Elasticsearch
        verify(mockSkillSearchRepository, times(1)).save(testSkill);
    }

    @Test
    @Transactional
    public void createSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillRepository.findAll().size();

        // Create the Skill with an existing ID
        skill.setId(1L);
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillMockMvc.perform(post("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeCreate);

        // Validate the Skill in Elasticsearch
        verify(mockSkillSearchRepository, times(0)).save(skill);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillRepository.findAll().size();
        // set the field null
        skill.setName(null);

        // Create the Skill, which fails.
        SkillDTO skillDTO = skillMapper.toDto(skill);

        restSkillMockMvc.perform(post("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isBadRequest());

        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillRepository.findAll().size();
        // set the field null
        skill.setType(null);

        // Create the Skill, which fails.
        SkillDTO skillDTO = skillMapper.toDto(skill);

        restSkillMockMvc.perform(post("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isBadRequest());

        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSkills() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList
        restSkillMockMvc.perform(get("/api/skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skill.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get the skill
        restSkillMockMvc.perform(get("/api/skills/{id}", skill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skill.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingSkill() throws Exception {
        // Get the skill
        restSkillMockMvc.perform(get("/api/skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        int databaseSizeBeforeUpdate = skillRepository.findAll().size();

        // Update the skill
        Skill updatedSkill = skillRepository.findById(skill.getId()).get();
        // Disconnect from session so that the updates on updatedSkill are not directly saved in db
        em.detach(updatedSkill);
        updatedSkill
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION);
        SkillDTO skillDTO = skillMapper.toDto(updatedSkill);

        restSkillMockMvc.perform(put("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isOk());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
        Skill testSkill = skillList.get(skillList.size() - 1);
        assertThat(testSkill.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSkill.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSkill.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Skill in Elasticsearch
        verify(mockSkillSearchRepository, times(1)).save(testSkill);
    }

    @Test
    @Transactional
    public void updateNonExistingSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillMockMvc.perform(put("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Skill in Elasticsearch
        verify(mockSkillSearchRepository, times(0)).save(skill);
    }

    @Test
    @Transactional
    public void deleteSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        int databaseSizeBeforeDelete = skillRepository.findAll().size();

        // Delete the skill
        restSkillMockMvc.perform(delete("/api/skills/{id}", skill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Skill in Elasticsearch
        verify(mockSkillSearchRepository, times(1)).deleteById(skill.getId());
    }

    @Test
    @Transactional
    public void searchSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);
        when(mockSkillSearchRepository.search(queryStringQuery("id:" + skill.getId())))
            .thenReturn(Collections.singletonList(skill));
        // Search the skill
        restSkillMockMvc.perform(get("/api/_search/skills?query=id:" + skill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skill.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Skill.class);
        Skill skill1 = new Skill();
        skill1.setId(1L);
        Skill skill2 = new Skill();
        skill2.setId(skill1.getId());
        assertThat(skill1).isEqualTo(skill2);
        skill2.setId(2L);
        assertThat(skill1).isNotEqualTo(skill2);
        skill1.setId(null);
        assertThat(skill1).isNotEqualTo(skill2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillDTO.class);
        SkillDTO skillDTO1 = new SkillDTO();
        skillDTO1.setId(1L);
        SkillDTO skillDTO2 = new SkillDTO();
        assertThat(skillDTO1).isNotEqualTo(skillDTO2);
        skillDTO2.setId(skillDTO1.getId());
        assertThat(skillDTO1).isEqualTo(skillDTO2);
        skillDTO2.setId(2L);
        assertThat(skillDTO1).isNotEqualTo(skillDTO2);
        skillDTO1.setId(null);
        assertThat(skillDTO1).isNotEqualTo(skillDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(skillMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(skillMapper.fromId(null)).isNull();
    }
}
