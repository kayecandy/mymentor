package com.hc.mymentor.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.hc.mymentor.domain.Mentee} entity.
 */
public class MenteeDTO implements Serializable {

    private Long id;

    private String mobilePhone;

    private String landline;

    private Boolean profileVisibleInInternet;

    @Size(max = 255)
    private String ownWebsiteUrl;

    @Size(max = 255)
    private String xingProfileUrl;

    @Size(max = 255)
    private String linkedInProfileUrl;


    private Long userId;

    private String userLogin;

    private Long addressId;

    private Set<PossessedSkillDTO> possessedSkills = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public Boolean isProfileVisibleInInternet() {
        return profileVisibleInInternet;
    }

    public void setProfileVisibleInInternet(Boolean profileVisibleInInternet) {
        this.profileVisibleInInternet = profileVisibleInInternet;
    }

    public String getOwnWebsiteUrl() {
        return ownWebsiteUrl;
    }

    public void setOwnWebsiteUrl(String ownWebsiteUrl) {
        this.ownWebsiteUrl = ownWebsiteUrl;
    }

    public String getXingProfileUrl() {
        return xingProfileUrl;
    }

    public void setXingProfileUrl(String xingProfileUrl) {
        this.xingProfileUrl = xingProfileUrl;
    }

    public String getLinkedInProfileUrl() {
        return linkedInProfileUrl;
    }

    public void setLinkedInProfileUrl(String linkedInProfileUrl) {
        this.linkedInProfileUrl = linkedInProfileUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Set<PossessedSkillDTO> getPossessedSkills() {
        return possessedSkills;
    }

    public void setPossessedSkills(Set<PossessedSkillDTO> possessedSkills) {
        this.possessedSkills = possessedSkills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenteeDTO menteeDTO = (MenteeDTO) o;
        if (menteeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), menteeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MenteeDTO{" +
            "id=" + getId() +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", landline='" + getLandline() + "'" +
            ", profileVisibleInInternet='" + isProfileVisibleInInternet() + "'" +
            ", ownWebsiteUrl='" + getOwnWebsiteUrl() + "'" +
            ", xingProfileUrl='" + getXingProfileUrl() + "'" +
            ", linkedInProfileUrl='" + getLinkedInProfileUrl() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            ", address=" + getAddressId() +
            "}";
    }
}
