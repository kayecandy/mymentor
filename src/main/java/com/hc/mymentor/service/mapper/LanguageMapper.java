package com.hc.mymentor.service.mapper;

import com.hc.mymentor.domain.*;
import com.hc.mymentor.service.dto.LanguageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Language} and its DTO {@link LanguageDTO}.
 */
@Mapper(componentModel = "spring", uses = {MenteeMapper.class})
public interface LanguageMapper extends EntityMapper<LanguageDTO, Language> {

    @Mapping(source = "mentee.id", target = "menteeId")
    LanguageDTO toDto(Language language);

    @Mapping(source = "menteeId", target = "mentee")
    Language toEntity(LanguageDTO languageDTO);

    default Language fromId(Long id) {
        if (id == null) {
            return null;
        }
        Language language = new Language();
        language.setId(id);
        return language;
    }
}
