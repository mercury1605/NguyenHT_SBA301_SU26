package com.lab04.orchidmanagement.pojos;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "orchid")
public class Orchid implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orchid_id")
    private int orchidID;

    @Column(name = "orchid_name")
    @NotBlank(message = "Orchid name is required")
    private String orchidName;

    @Column(name = "is_natural", columnDefinition = "bit default 0")
    @NotNull(message = "Natural status is required")
    private boolean isNatural;

    @Column(name = "orchid_description")
    private String orchidDescription;

    @Column(name = "orchid_category")
    @NotBlank(message = "Orchid category is required")
    private String orchidCategory;

    @Column(name = "is_attractive", columnDefinition = "bit default 0")
    private boolean isAttractive;

    @Column(name = "orchid_url")
    private String orchidURL;

    public Orchid() {
    }

    public Orchid(Integer orchidId, String orchidName, Boolean isNatural,
            String orchidDescription, String orchidCategory,
            Boolean isAttractive, String orchidURL) {
        this.orchidID = orchidId;
        this.orchidName = orchidName;
        this.isNatural = isNatural;
        this.orchidDescription = orchidDescription;
        this.orchidCategory = orchidCategory;
        this.isAttractive = isAttractive;
        this.orchidURL = orchidURL;
    }

    // Getters and Setters
    public Integer getOrchidId() {
        return orchidID;
    }

    public void setOrchidId(Integer orchidId) {
        this.orchidID = orchidId;
    }

    public String getOrchidName() {
        return orchidName;
    }

    public void setOrchidName(String orchidName) {
        this.orchidName = orchidName;
    }

    public Boolean getIsNatural() {
        return isNatural;
    }

    public void setIsNatural(Boolean isNatural) {
        this.isNatural = isNatural;
    }

    public String getOrchidDescription() {
        return orchidDescription;
    }

    public void setOrchidDescription(String orchidDescription) {
        this.orchidDescription = orchidDescription;
    }

    public String getOrchidCategory() {
        return orchidCategory;
    }

    public void setOrchidCategory(String orchidCategory) {
        this.orchidCategory = orchidCategory;
    }

    public Boolean getIsAttractive() {
        return isAttractive;
    }

    public void setIsAttractive(Boolean isAttractive) {
        this.isAttractive = isAttractive;
    }

    public String getOrchidURL() {
        return orchidURL;
    }

    public void setOrchidURL(String orchidURL) {
        this.orchidURL = orchidURL;
    }

    @Override
    public String toString() {
        return "Orchid{" +
                "orchidId=" + orchidID +
                ", orchidName='" + orchidName + '\'' +
                ", isNatural=" + isNatural +
                ", orchidDescription='" + orchidDescription + '\'' +
                ", orchidCategory='" + orchidCategory + '\'' +
                ", isAttractive=" + isAttractive +
                ", orchidURL='" + orchidURL + '\'' +
                '}';
    }

}
