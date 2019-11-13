package com.hc.mymentor.web.rest;

import com.hc.mymentor.MymentorApp;
import com.hc.mymentor.domain.Certificate;
import com.hc.mymentor.domain.EduInstitution;
import com.hc.mymentor.repository.CertificateRepository;
import com.hc.mymentor.service.CertificateService;
import com.hc.mymentor.service.dto.CertificateDTO;
import com.hc.mymentor.service.mapper.CertificateMapper;
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
import java.util.List;

import static com.hc.mymentor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CertificateResource} REST controller.
 */
@SpringBootTest(classes = MymentorApp.class)
public class CertificateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ISSUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ISSUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EXPIRE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private CertificateMapper certificateMapper;

    @Autowired
    private CertificateService certificateService;

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

    private MockMvc restCertificateMockMvc;

    private Certificate certificate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CertificateResource certificateResource = new CertificateResource(certificateService);
        this.restCertificateMockMvc = MockMvcBuilders.standaloneSetup(certificateResource)
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
    public static Certificate createEntity(EntityManager em) {
        Certificate certificate = new Certificate()
            .name(DEFAULT_NAME)
            .issueDate(DEFAULT_ISSUE_DATE)
            .expireDate(DEFAULT_EXPIRE_DATE)
            .url(DEFAULT_URL);
        // Add required entity
        EduInstitution eduInstitution;
        if (TestUtil.findAll(em, EduInstitution.class).isEmpty()) {
            eduInstitution = EduInstitutionResourceIT.createEntity(em);
            em.persist(eduInstitution);
            em.flush();
        } else {
            eduInstitution = TestUtil.findAll(em, EduInstitution.class).get(0);
        }
        certificate.setInstitution(eduInstitution);
        return certificate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Certificate createUpdatedEntity(EntityManager em) {
        Certificate certificate = new Certificate()
            .name(UPDATED_NAME)
            .issueDate(UPDATED_ISSUE_DATE)
            .expireDate(UPDATED_EXPIRE_DATE)
            .url(UPDATED_URL);
        // Add required entity
        EduInstitution eduInstitution;
        if (TestUtil.findAll(em, EduInstitution.class).isEmpty()) {
            eduInstitution = EduInstitutionResourceIT.createUpdatedEntity(em);
            em.persist(eduInstitution);
            em.flush();
        } else {
            eduInstitution = TestUtil.findAll(em, EduInstitution.class).get(0);
        }
        certificate.setInstitution(eduInstitution);
        return certificate;
    }

    @BeforeEach
    public void initTest() {
        certificate = createEntity(em);
    }

    @Test
    @Transactional
    public void createCertificate() throws Exception {
        int databaseSizeBeforeCreate = certificateRepository.findAll().size();

        // Create the Certificate
        CertificateDTO certificateDTO = certificateMapper.toDto(certificate);
        restCertificateMockMvc.perform(post("/api/certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificateDTO)))
            .andExpect(status().isCreated());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeCreate + 1);
        Certificate testCertificate = certificateList.get(certificateList.size() - 1);
        assertThat(testCertificate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCertificate.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
        assertThat(testCertificate.getExpireDate()).isEqualTo(DEFAULT_EXPIRE_DATE);
        assertThat(testCertificate.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createCertificateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = certificateRepository.findAll().size();

        // Create the Certificate with an existing ID
        certificate.setId(1L);
        CertificateDTO certificateDTO = certificateMapper.toDto(certificate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCertificateMockMvc.perform(post("/api/certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = certificateRepository.findAll().size();
        // set the field null
        certificate.setName(null);

        // Create the Certificate, which fails.
        CertificateDTO certificateDTO = certificateMapper.toDto(certificate);

        restCertificateMockMvc.perform(post("/api/certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificateDTO)))
            .andExpect(status().isBadRequest());

        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIssueDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = certificateRepository.findAll().size();
        // set the field null
        certificate.setIssueDate(null);

        // Create the Certificate, which fails.
        CertificateDTO certificateDTO = certificateMapper.toDto(certificate);

        restCertificateMockMvc.perform(post("/api/certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificateDTO)))
            .andExpect(status().isBadRequest());

        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCertificates() throws Exception {
        // Initialize the database
        certificateRepository.saveAndFlush(certificate);

        // Get all the certificateList
        restCertificateMockMvc.perform(get("/api/certificates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certificate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].expireDate").value(hasItem(DEFAULT_EXPIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }
    
    @Test
    @Transactional
    public void getCertificate() throws Exception {
        // Initialize the database
        certificateRepository.saveAndFlush(certificate);

        // Get the certificate
        restCertificateMockMvc.perform(get("/api/certificates/{id}", certificate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(certificate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.issueDate").value(DEFAULT_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.expireDate").value(DEFAULT_EXPIRE_DATE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }

    @Test
    @Transactional
    public void getNonExistingCertificate() throws Exception {
        // Get the certificate
        restCertificateMockMvc.perform(get("/api/certificates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCertificate() throws Exception {
        // Initialize the database
        certificateRepository.saveAndFlush(certificate);

        int databaseSizeBeforeUpdate = certificateRepository.findAll().size();

        // Update the certificate
        Certificate updatedCertificate = certificateRepository.findById(certificate.getId()).get();
        // Disconnect from session so that the updates on updatedCertificate are not directly saved in db
        em.detach(updatedCertificate);
        updatedCertificate
            .name(UPDATED_NAME)
            .issueDate(UPDATED_ISSUE_DATE)
            .expireDate(UPDATED_EXPIRE_DATE)
            .url(UPDATED_URL);
        CertificateDTO certificateDTO = certificateMapper.toDto(updatedCertificate);

        restCertificateMockMvc.perform(put("/api/certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificateDTO)))
            .andExpect(status().isOk());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeUpdate);
        Certificate testCertificate = certificateList.get(certificateList.size() - 1);
        assertThat(testCertificate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCertificate.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testCertificate.getExpireDate()).isEqualTo(UPDATED_EXPIRE_DATE);
        assertThat(testCertificate.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingCertificate() throws Exception {
        int databaseSizeBeforeUpdate = certificateRepository.findAll().size();

        // Create the Certificate
        CertificateDTO certificateDTO = certificateMapper.toDto(certificate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificateMockMvc.perform(put("/api/certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(certificateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Certificate in the database
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCertificate() throws Exception {
        // Initialize the database
        certificateRepository.saveAndFlush(certificate);

        int databaseSizeBeforeDelete = certificateRepository.findAll().size();

        // Delete the certificate
        restCertificateMockMvc.perform(delete("/api/certificates/{id}", certificate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Certificate> certificateList = certificateRepository.findAll();
        assertThat(certificateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Certificate.class);
        Certificate certificate1 = new Certificate();
        certificate1.setId(1L);
        Certificate certificate2 = new Certificate();
        certificate2.setId(certificate1.getId());
        assertThat(certificate1).isEqualTo(certificate2);
        certificate2.setId(2L);
        assertThat(certificate1).isNotEqualTo(certificate2);
        certificate1.setId(null);
        assertThat(certificate1).isNotEqualTo(certificate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CertificateDTO.class);
        CertificateDTO certificateDTO1 = new CertificateDTO();
        certificateDTO1.setId(1L);
        CertificateDTO certificateDTO2 = new CertificateDTO();
        assertThat(certificateDTO1).isNotEqualTo(certificateDTO2);
        certificateDTO2.setId(certificateDTO1.getId());
        assertThat(certificateDTO1).isEqualTo(certificateDTO2);
        certificateDTO2.setId(2L);
        assertThat(certificateDTO1).isNotEqualTo(certificateDTO2);
        certificateDTO1.setId(null);
        assertThat(certificateDTO1).isNotEqualTo(certificateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(certificateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(certificateMapper.fromId(null)).isNull();
    }
}
