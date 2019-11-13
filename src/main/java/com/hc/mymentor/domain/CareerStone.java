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
 * This entity represent a Job that the mentee hab in his or her career.\nAll the career stones of a mentee combined represent his work experience.
 */
@Entity
@Table(name = "career_stone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "careerstone")
public class CareerStone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(max = 127)
    @Column(name = "title", length = 127, nullable = false)
    private String title;

    @NotNull
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "still_working_here")
    private Boolean stillWorkingHere;

    @Column(name = "location")
    private String location;

    @Size(max = 1023)
    @Column(name = "description", length = 1023)
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("careerStones")
    private Company company;

    @ManyToOne
    @JsonIgnoreProperties("careerStones")
    private Mentee mentee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public CareerStone title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public CareerStone fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public CareerStone toDate(LocalDate toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Boolean isStillWorkingHere() {
        return stillWorkingHere;
    }

    public CareerStone stillWorkingHere(Boolean stillWorkingHere) {
        this.stillWorkingHere = stillWorkingHere;
        return this;
    }

    public void setStillWorkingHere(Boolean stillWorkingHere) {
        this.stillWorkingHere = stillWorkingHere;
    }

    public String getLocation() {
        return location;
    }

    public CareerStone location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public CareerStone description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Company getCompany() {
        return company;
    }

    public CareerStone company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Mentee getMentee() {
        return mentee;
    }

    public CareerStone mentee(Mentee mentee) {
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
        if (!(o instanceof CareerStone)) {
            return false;
        }
        return id != null && id.equals(((CareerStone) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CareerStone{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", stillWorkingHere='" + isStillWorkingHere() + "'" +
            ", location='" + getLocation() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
