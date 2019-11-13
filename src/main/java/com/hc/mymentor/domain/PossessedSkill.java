package com.hc.mymentor.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PossessedSkill.
 */
@Entity
@Table(name = "possessed_skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "possessedskill")
public class PossessedSkill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "top_skill")
    private Boolean topSkill;

    @ManyToOne
    @JsonIgnoreProperties("possessedSkills")
    private Skill skill;

    @ManyToMany(mappedBy = "possessedSkills")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Mentee> mentees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isTopSkill() {
        return topSkill;
    }

    public PossessedSkill topSkill(Boolean topSkill) {
        this.topSkill = topSkill;
        return this;
    }

    public void setTopSkill(Boolean topSkill) {
        this.topSkill = topSkill;
    }

    public Skill getSkill() {
        return skill;
    }

    public PossessedSkill skill(Skill skill) {
        this.skill = skill;
        return this;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Set<Mentee> getMentees() {
        return mentees;
    }

    public PossessedSkill mentees(Set<Mentee> mentees) {
        this.mentees = mentees;
        return this;
    }

    public PossessedSkill addMentees(Mentee mentee) {
        this.mentees.add(mentee);
        mentee.getPossessedSkills().add(this);
        return this;
    }

    public PossessedSkill removeMentees(Mentee mentee) {
        this.mentees.remove(mentee);
        mentee.getPossessedSkills().remove(this);
        return this;
    }

    public void setMentees(Set<Mentee> mentees) {
        this.mentees = mentees;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PossessedSkill)) {
            return false;
        }
        return id != null && id.equals(((PossessedSkill) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PossessedSkill{" +
            "id=" + getId() +
            ", topSkill='" + isTopSkill() + "'" +
            "}";
    }
}
