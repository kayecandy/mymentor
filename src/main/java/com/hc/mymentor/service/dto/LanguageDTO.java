package com.hc.mymentor.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.hc.mymentor.domain.Language} entity.
 */
public class LanguageDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 127)
    private String name;


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

        LanguageDTO languageDTO = (LanguageDTO) o;
        if (languageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), languageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LanguageDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", mentee=" + getMenteeId() +
            "}";
    }
}
