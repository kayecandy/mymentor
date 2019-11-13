package com.hc.mymentor.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.hc.mymentor.domain.PossessedSkill} entity.
 */
public class PossessedSkillDTO implements Serializable {

    private Long id;

    private Boolean topSkill;


    private Long skillId;

    private String skillName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isTopSkill() {
        return topSkill;
    }

    public void setTopSkill(Boolean topSkill) {
        this.topSkill = topSkill;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PossessedSkillDTO possessedSkillDTO = (PossessedSkillDTO) o;
        if (possessedSkillDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), possessedSkillDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PossessedSkillDTO{" +
            "id=" + getId() +
            ", topSkill='" + isTopSkill() + "'" +
            ", skill=" + getSkillId() +
            ", skill='" + getSkillName() + "'" +
            "}";
    }
}
