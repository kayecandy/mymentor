package com.hc.mymentor.service.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.hc.mymentor.domain.EduStone} entity.
 */
@ApiModel(description = "This class represents an education of the mentee.\nFor example Bachelor of Sience in London from 2018 till 2012.")
public class EduStoneDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 127)
    private String degree;

    @Size(max = 31)
    private String grade;

    @NotNull
    private LocalDate fromDate;

    /**
     * Ended or expected to end.
     */
    @NotNull
    @ApiModelProperty(value = "Ended or expected to end.", required = true)
    private LocalDate toDate;

    @Size(max = 1023)
    private String description;

    @Size(max = 1023)
    private String activities;


    private Long schoolId;

    private String schoolName;

    private Long menteeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long eduInstitutionId) {
        this.schoolId = eduInstitutionId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String eduInstitutionName) {
        this.schoolName = eduInstitutionName;
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

        EduStoneDTO eduStoneDTO = (EduStoneDTO) o;
        if (eduStoneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eduStoneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EduStoneDTO{" +
            "id=" + getId() +
            ", degree='" + getDegree() + "'" +
            ", grade='" + getGrade() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", activities='" + getActivities() + "'" +
            ", school=" + getSchoolId() +
            ", school='" + getSchoolName() + "'" +
            ", mentee=" + getMenteeId() +
            "}";
    }
}
