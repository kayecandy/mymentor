package com.hc.mymentor.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * This class represents an education of the mentee.\nFor example Bachelor of Sience in London from 2018 till 2012.
 */
@Entity
@Table(name = "edu_stone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "edustone")
public class EduStone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(max = 127)
    @Column(name = "degree", length = 127, nullable = false)
    private String degree;

    @Size(max = 31)
    @Column(name = "grade", length = 31)
    private String grade;

    @NotNull
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    /**
     * Ended or expected to end.
     */
    @NotNull
    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @Size(max = 1023)
    @Column(name = "description", length = 1023)
    private String description;

    @Size(max = 1023)
    @Column(name = "activities", length = 1023)
    private String activities;

    @ManyToOne
    @JsonIgnoreProperties("eduStones")
    private EduInstitution school;

    @ManyToOne
    @JsonIgnoreProperties("eduStones")
    private Mentee mentee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public EduStone degree(String degree) {
        this.degree = degree;
        return this;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getGrade() {
        return grade;
    }

    public EduStone grade(String grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public EduStone fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public EduStone toDate(LocalDate toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getDescription() {
        return description;
    }

    public EduStone description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivities() {
        return activities;
    }

    public EduStone activities(String activities) {
        this.activities = activities;
        return this;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public EduInstitution getSchool() {
        return school;
    }

    public EduStone school(EduInstitution eduInstitution) {
        this.school = eduInstitution;
        return this;
    }

    public void setSchool(EduInstitution eduInstitution) {
        this.school = eduInstitution;
    }

    public Mentee getMentee() {
        return mentee;
    }

    public EduStone mentee(Mentee mentee) {
        this.mentee = mentee;
        return this;
    }

    public void setMentee(Mentee mentee) {
        this.mentee = mentee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EduStone)) {
            return false;
        }
        return id != null && id.equals(((EduStone) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EduStone{" +
            "id=" + getId() +
            ", degree='" + getDegree() + "'" +
            ", grade='" + getGrade() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", activities='" + getActivities() + "'" +
            "}";
    }
}
