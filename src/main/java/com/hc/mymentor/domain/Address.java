package com.hc.mymentor.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * The field country will be ignored at first, becouse at the beginning we will only operate in Germany.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 127)
    @Column(name = "street", length = 127, nullable = false)
    private String street;

    @NotNull
    @Size(max = 7)
    @Column(name = "street_number", length = 7, nullable = false)
    private String streetNumber;

    @Size(max = 127)
    @Column(name = "second_line", length = 127)
    private String secondLine;

    /**
     * For the beginning we only let german postcodes.
     */
    @NotNull
    @Size(max = 10)
    @Column(name = "postcode", length = 10, nullable = false)
    private String postcode;

    @NotNull
    @Size(max = 127)
    @Column(name = "city", length = 127, nullable = false)
    private String city;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public Address street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public Address streetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getSecondLine() {
        return secondLine;
    }

    public Address secondLine(String secondLine) {
        this.secondLine = secondLine;
        return this;
    }

    public void setSecondLine(String secondLine) {
        this.secondLine = secondLine;
    }

    public String getPostcode() {
        return postcode;
    }

    public Address postcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", street='" + getStreet() + "'" +
            ", streetNumber='" + getStreetNumber() + "'" +
            ", secondLine='" + getSecondLine() + "'" +
            ", postcode='" + getPostcode() + "'" +
            ", city='" + getCity() + "'" +
            "}";
    }
}
