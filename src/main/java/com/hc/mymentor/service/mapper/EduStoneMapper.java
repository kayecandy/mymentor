package com.hc.mymentor.service.mapper;

import com.hc.mymentor.domain.*;
import com.hc.mymentor.service.dto.EduStoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EduStone} and its DTO {@link EduStoneDTO}.
 */
@Mapper(componentModel = "spring", uses = {EduInstitutionMapper.class, MenteeMapper.class})
public interface EduStoneMapper extends EntityMapper<EduStoneDTO, EduStone> {

    @Mapping(source = "school.id", target = "schoolId")
    @Mapping(source = "school.name", target = "schoolName")
    @Mapping(source = "mentee.id", target = "menteeId")
    EduStoneDTO toDto(EduStone eduStone);

    @Mapping(source = "schoolId", target = "school")
    @Mapping(source = "menteeId", target = "mentee")
    EduStone toEntity(EduStoneDTO eduStoneDTO);

    default EduStone fromId(Long id) {
        if (id == null) {
            return null;
        }
        EduStone eduStone = new EduStone();
        eduStone.setId(id);
        return eduStone;
    }
}
