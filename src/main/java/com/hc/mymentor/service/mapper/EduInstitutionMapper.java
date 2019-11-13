package com.hc.mymentor.service.mapper;

import com.hc.mymentor.domain.*;
import com.hc.mymentor.service.dto.EduInstitutionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EduInstitution} and its DTO {@link EduInstitutionDTO}.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface EduInstitutionMapper extends EntityMapper<EduInstitutionDTO, EduInstitution> {

    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "address.city", target = "addressCity")
    EduInstitutionDTO toDto(EduInstitution eduInstitution);

    @Mapping(source = "addressId", target = "address")
    EduInstitution toEntity(EduInstitutionDTO eduInstitutionDTO);

    default EduInstitution fromId(Long id) {
        if (id == null) {
            return null;
        }
        EduInstitution eduInstitution = new EduInstitution();
        eduInstitution.setId(id);
        return eduInstitution;
    }
}
