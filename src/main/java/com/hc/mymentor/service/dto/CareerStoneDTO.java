package com.hc.mymentor.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.hc.mymentor.domain.CareerStone} entity.
 */
@ApiModel(description = "This entity represent a Job that the mentee hab in his or her career.\nAll the career stones of a mentee combined represent his work experience.")
public class CareerStoneDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 127)
    private String title;

    @NotNull
    private LocalDate fromDate;

    private LocalDate toDate;

    private Boolean stillWorkingHere;

    private String location;

    @Size(max = 1023)
    private String description;


    private Long companyId;

    private String companyName;

    private Long menteeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Boolean isStillWorkingHere() {
        return stillWorkingHere;
    }

    public void setStillWorkingHere(Boolean stillWorkingHere) {
        this.stillWorkingHere = stillWorkingHere;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

        CareerStoneDTO careerStoneDTO = (CareerStoneDTO) o;
        if (careerStoneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), careerStoneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CareerStoneDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", stillWorkingHere='" + isStillWorkingHere() + "'" +
            ", location='" + getLocation() + "'" +
            ", description='" + getDescription() + "'" +
            ", company=" + getCompanyId() +
            ", company='" + getCompanyName() + "'" +
            ", mentee=" + getMenteeId() +
            "}";
    }
}
