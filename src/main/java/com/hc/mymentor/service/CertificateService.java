package com.hc.mymentor.service;

import com.hc.mymentor.domain.Certificate;
import com.hc.mymentor.repository.CertificateRepository;
import com.hc.mymentor.service.dto.CertificateDTO;
import com.hc.mymentor.service.mapper.CertificateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Certificate}.
 */
@Service
@Transactional
public class CertificateService {

    private final Logger log = LoggerFactory.getLogger(CertificateService.class);

    private final CertificateRepository certificateRepository;

    private final CertificateMapper certificateMapper;

    public CertificateService(CertificateRepository certificateRepository, CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }

    /**
     * Save a certificate.
     *
     * @param certificateDTO the entity to save.
     * @return the persisted entity.
     */
    public CertificateDTO save(CertificateDTO certificateDTO) {
        log.debug("Request to save Certificate : {}", certificateDTO);
        Certificate certificate = certificateMapper.toEntity(certificateDTO);
        certificate = certificateRepository.save(certificate);
        return certificateMapper.toDto(certificate);
    }

    /**
     * Get all the certificates.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CertificateDTO> findAll() {
        log.debug("Request to get all Certificates");
        return certificateRepository.findAll().stream()
            .map(certificateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one certificate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CertificateDTO> findOne(Long id) {
        log.debug("Request to get Certificate : {}", id);
        return certificateRepository.findById(id)
            .map(certificateMapper::toDto);
    }

    /**
     * Delete the certificate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Certificate : {}", id);
        certificateRepository.deleteById(id);
    }
}
