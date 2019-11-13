package com.hc.mymentor.service.mapper;

import com.hc.mymentor.domain.*;
import com.hc.mymentor.service.dto.CompanyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Company} and its DTO {@link CompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {

    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "address.city", target = "addressCity")
    CompanyDTO toDto(Company company);

    @Mapping(source = "addressId", target = "address")
    Company toEntity(CompanyDTO companyDTO);

    default Company fromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}
