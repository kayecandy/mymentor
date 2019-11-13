package com.hc.mymentor.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.hc.mymentor.domain.Certificate} entity.
 */
@ApiModel(description = "This entity represent a certificate.")
public class CertificateDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 127)
    private String name;

    @NotNull
    private LocalDate issueDate;

    private LocalDate expireDate;

    @Size(max = 255)
    private String url;

    /**
     * Issuing Institution.
     */
    @ApiModelProperty(value = "Issuing Institution.")

    private Long institutionId;

    private String institutionName;

    private Long menteeId;

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

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Long eduInstitutionId) {
        this.institutionId = eduInstitutionId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String eduInstitutionName) {
        this.institutionName = eduInstitutionName;
    }

    public Long getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(Long menteeId) {
        this.menteeId = menteeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CertificateDTO certificateDTO = (CertificateDTO) o;
        if (certificateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), certificateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CertificateDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", issueDate='" + getIssueDate() + "'" +
            ", expireDate='" + getExpireDate() + "'" +
            ", url='" + getUrl() + "'" +
            ", institution=" + getInstitutionId() +
            ", institution='" + getInstitutionName() + "'" +
            ", mentee=" + getMenteeId() +
            "}";
    }
}
