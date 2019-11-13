package com.hc.mymentor.service.dto;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.hc.mymentor.domain.EduInstitution} entity.
 */
@ApiModel(description = "This entity represent an educational institution, like a school or university.")
public class EduInstitutionDTO implements Serializable {

    private Long id;

    @Size(max = 127)
    private String name;

    @Size(max = 1023)
    private String description;

    @Size(max = 255)
    private String url;


    private Long addressId;

    private String addressCity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EduInstitutionDTO eduInstitutionDTO = (EduInstitutionDTO) o;
        if (eduInstitutionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eduInstitutionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EduInstitutionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", url='" + getUrl() + "'" +
            ", address=" + getAddressId() +
            ", address='" + getAddressCity() + "'" +
            "}";
    }
}
