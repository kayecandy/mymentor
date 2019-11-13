package com.hc.mymentor.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Mentee.
 */
@Entity
@Table(name = "mentee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "mentee")
public class Mentee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "landline")
    private String landline;

    @Column(name = "profile_visible_in_internet")
    private Boolean profileVisibleInInternet;

    @Size(max = 255)
    @Column(name = "own_website_url", length = 255)
    private String ownWebsiteUrl;

    @Size(max = 255)
    @Column(name = "xing_profile_url", length = 255)
    private String xingProfileUrl;

    @Size(max = 255)
    @Column(name = "linked_in_profile_url", length = 255)
    private String linkedInProfileUrl;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "mentee")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CareerStone> careerStones = new HashSet<>();

    @OneToMany(mappedBy = "mentee")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EduStone> eduStones = new HashSet<>();

    @OneToMany(mappedBy = "mentee")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Certificate> certificates = new HashSet<>();

    @OneToMany(mappedBy = "mentee")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Language> languages = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "mentee_possessed_skill",
               joinColumns = @JoinColumn(name = "mentee_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "possessed_skill_id", referencedColumnName = "id"))
    private Set<PossessedSkill> possessedSkills = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public Mentee mobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getLandline() {
        return landline;
    }

    public Mentee landline(String landline) {
        this.landline = landline;
        return this;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public Boolean isProfileVisibleInInternet() {
        return profileVisibleInInternet;
    }

    public Mentee profileVisibleInInternet(Boolean profileVisibleInInternet) {
        this.profileVisibleInInternet = profileVisibleInInternet;
        return this;
    }

    public void setProfileVisibleInInternet(Boolean profileVisibleInInternet) {
        this.profileVisibleInInternet = profileVisibleInInternet;
    }

    public String getOwnWebsiteUrl() {
        return ownWebsiteUrl;
    }

    public Mentee ownWebsiteUrl(String ownWebsiteUrl) {
        this.ownWebsiteUrl = ownWebsiteUrl;
        return this;
    }

    public void setOwnWebsiteUrl(String ownWebsiteUrl) {
        this.ownWebsiteUrl = ownWebsiteUrl;
    }

    public String getXingProfileUrl() {
        return xingProfileUrl;
    }

    public Mentee xingProfileUrl(String xingProfileUrl) {
        this.xingProfileUrl = xingProfileUrl;
        return this;
    }

    public void setXingProfileUrl(String xingProfileUrl) {
        this.xingProfileUrl = xingProfileUrl;
    }

    public String getLinkedInProfileUrl() {
        return linkedInProfileUrl;
    }

    public Mentee linkedInProfileUrl(String linkedInProfileUrl) {
        this.linkedInProfileUrl = linkedInProfileUrl;
        return this;
    }

    public void setLinkedInProfileUrl(String linkedInProfileUrl) {
        this.linkedInProfileUrl = linkedInProfileUrl;
    }

    public User getUser() {
        return user;
    }

    public Mentee user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public Mentee address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<CareerStone> getCareerStones() {
        return careerStones;
    }

    public Mentee careerStones(Set<CareerStone> careerStones) {
        this.careerStones = careerStones;
        return this;
    }

    public Mentee addCareerStones(CareerStone careerStone) {
        this.careerStones.add(careerStone);
        careerStone.setMentee(this);
        return this;
    }

    public Mentee removeCareerStones(CareerStone careerStone) {
        this.careerStones.remove(careerStone);
        careerStone.setMentee(null);
        return this;
    }

    public void setCareerStones(Set<CareerStone> careerStones) {
        this.careerStones = careerStones;
    }

    public Set<EduStone> getEduStones() {
        return eduStones;
    }

    public Mentee eduStones(Set<EduStone> eduStones) {
        this.eduStones = eduStones;
        return this;
    }

    public Mentee addEduStones(EduStone eduStone) {
        this.eduStones.add(eduStone);
        eduStone.setMentee(this);
        return this;
    }

    public Mentee removeEduStones(EduStone eduStone) {
        this.eduStones.remove(eduStone);
        eduStone.setMentee(null);
        return this;
    }

    public void setEduStones(Set<EduStone> eduStones) {
        this.eduStones = eduStones;
    }

    public Set<Certificate> getCertificates() {
        return certificates;
    }

    public Mentee certificates(Set<Certificate> certificates) {
        this.certificates = certificates;
        return this;
    }

    public Mentee addCertificates(Certificate certificate) {
        this.certificates.add(certificate);
        certificate.setMentee(this);
        return this;
    }

    public Mentee removeCertificates(Certificate certificate) {
        this.certificates.remove(certificate);
        certificate.setMentee(null);
        return this;
    }

    public void setCertificates(Set<Certificate> certificates) {
        this.certificates = certificates;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public Mentee languages(Set<Language> languages) {
        this.languages = languages;
        return this;
    }

    public Mentee addLanguages(Language language) {
        this.languages.add(language);
        language.setMentee(this);
        return this;
    }

    public Mentee removeLanguages(Language language) {
        this.languages.remove(language);
        language.setMentee(null);
        return this;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public Set<PossessedSkill> getPossessedSkills() {
        return possessedSkills;
    }

    public Mentee possessedSkills(Set<PossessedSkill> possessedSkills) {
        this.possessedSkills = possessedSkills;
        return this;
    }

    public Mentee addPossessedSkill(PossessedSkill possessedSkill) {
        this.possessedSkills.add(possessedSkill);
        possessedSkill.getMentees().add(this);
        return this;
    }

    public Mentee removePossessedSkill(PossessedSkill possessedSkill) {
        this.possessedSkills.remove(possessedSkill);
        possessedSkill.getMentees().remove(this);
        return this;
    }

    public void setPossessedSkills(Set<PossessedSkill> possessedSkills) {
        this.possessedSkills = possessedSkills;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mentee)) {
            return false;
        }
        return id != null && id.equals(((Mentee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Mentee{" +
            "id=" + getId() +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", landline='" + getLandline() + "'" +
            ", profileVisibleInInternet='" + isProfileVisibleInInternet() + "'" +
            ", ownWebsiteUrl='" + getOwnWebsiteUrl() + "'" +
            ", xingProfileUrl='" + getXingProfileUrl() + "'" +
            ", linkedInProfileUrl='" + getLinkedInProfileUrl() + "'" +
            "}";
    }
}
