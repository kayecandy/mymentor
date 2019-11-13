package com.hc.mymentor.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * This entity represent a certificate.
 */
@Entity
@Table(name = "certificate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Certificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 127)
    @Column(name = "name", length = 127, nullable = false)
    private String name;

    @NotNull
    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    @Size(max = 255)
    @Column(name = "url", length = 255)
    private String url;

    /**
     * Issuing Institution.
     */
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("certificates")
    private EduInstitution institution;

    @ManyToOne
    @JsonIgnoreProperties("certificates")
    private Mentee mentee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Certificate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public Certificate issueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public Certificate expireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
        return this;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public String getUrl() {
        return url;
    }

    public Certificate url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public EduInstitution getInstitution() {
        return institution;
    }

    public Certificate institution(EduInstitution eduInstitution) {
        this.institution = eduInstitution;
        return this;
    }

    public void setInstitution(EduInstitution eduInstitution) {
        this.institution = eduInstitution;
    }

    public Mentee getMentee() {
        return mentee;
    }

    public Certificate mentee(Mentee mentee) {
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
        if (!(o instanceof Certificate)) {
            return false;
        }
        return id != null && id.equals(((Certificate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Certificate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", issueDate='" + getIssueDate() + "'" +
            ", expireDate='" + getExpireDate() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
