package com.hc.mymentor.service.mapper;

import com.hc.mymentor.domain.*;
import com.hc.mymentor.service.dto.PossessedSkillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PossessedSkill} and its DTO {@link PossessedSkillDTO}.
 */
@Mapper(componentModel = "spring", uses = {SkillMapper.class})
public interface PossessedSkillMapper extends EntityMapper<PossessedSkillDTO, PossessedSkill> {

    @Mapping(source = "skill.id", target = "skillId")
    @Mapping(source = "skill.name", target = "skillName")
    PossessedSkillDTO toDto(PossessedSkill possessedSkill);

    @Mapping(source = "skillId", target = "skill")
    @Mapping(target = "mentees", ignore = true)
    @Mapping(target = "removeMentees", ignore = true)
    PossessedSkill toEntity(PossessedSkillDTO possessedSkillDTO);

    default PossessedSkill fromId(Long id) {
        if (id == null) {
            return null;
        }
        PossessedSkill possessedSkill = new PossessedSkill();
        possessedSkill.setId(id);
        return possessedSkill;
    }
}
