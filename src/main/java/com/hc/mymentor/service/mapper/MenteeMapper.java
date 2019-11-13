package com.hc.mymentor.service.mapper;

import com.hc.mymentor.domain.*;
import com.hc.mymentor.service.dto.MenteeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Mentee} and its DTO {@link MenteeDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AddressMapper.class, PossessedSkillMapper.class})
public interface MenteeMapper extends EntityMapper<MenteeDTO, Mentee> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "address.id", target = "addressId")
    MenteeDTO toDto(Mentee mentee);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "addressId", target = "address")
    @Mapping(target = "careerStones", ignore = true)
    @Mapping(target = "removeCareerStones", ignore = true)
    @Mapping(target = "eduStones", ignore = true)
    @Mapping(target = "removeEduStones", ignore = true)
    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "removeCertificates", ignore = true)
    @Mapping(target = "languages", ignore = true)
    @Mapping(target = "removeLanguages", ignore = true)
    @Mapping(target = "removePossessedSkill", ignore = true)
    Mentee toEntity(MenteeDTO menteeDTO);

    default Mentee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mentee mentee = new Mentee();
        mentee.setId(id);
        return mentee;
    }
}
