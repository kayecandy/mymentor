package com.hc.mymentor.service.mapper;

import com.hc.mymentor.domain.*;
import com.hc.mymentor.service.dto.CareerStoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CareerStone} and its DTO {@link CareerStoneDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class, MenteeMapper.class})
public interface CareerStoneMapper extends EntityMapper<CareerStoneDTO, CareerStone> {

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "mentee.id", target = "menteeId")
    CareerStoneDTO toDto(CareerStone careerStone);

    @Mapping(source = "companyId", target = "company")
    @Mapping(source = "menteeId", target = "mentee")
    CareerStone toEntity(CareerStoneDTO careerStoneDTO);

    default CareerStone fromId(Long id) {
        if (id == null) {
            return null;
        }
        CareerStone careerStone = new CareerStone();
        careerStone.setId(id);
        return careerStone;
    }
}
