package com.hc.mymentor.service.mapper;

import com.hc.mymentor.domain.*;
import com.hc.mymentor.service.dto.CertificateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Certificate} and its DTO {@link CertificateDTO}.
 */
@Mapper(componentModel = "spring", uses = {EduInstitutionMapper.class, MenteeMapper.class})
public interface CertificateMapper extends EntityMapper<CertificateDTO, Certificate> {

    @Mapping(source = "institution.id", target = "institutionId")
    @Mapping(source = "institution.name", target = "institutionName")
    @Mapping(source = "mentee.id", target = "menteeId")
    CertificateDTO toDto(Certificate certificate);

    @Mapping(source = "institutionId", target = "institution")
    @Mapping(source = "menteeId", target = "mentee")
    Certificate toEntity(CertificateDTO certificateDTO);

    default Certificate fromId(Long id) {
        if (id == null) {
            return null;
        }
        Certificate certificate = new Certificate();
        certificate.setId(id);
        return certificate;
    }
}
