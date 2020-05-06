package org.fininfo.bazarv3.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.fininfo.bazarv3.domain.Adresse} entity.
 */
public class AdresseDTO implements Serializable {
    
    private Long id;

    private Boolean principale;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    @NotNull
    private Integer adresseLigne1;

    private Integer adresseLigne2;

    @NotNull
    private String gouvernorat;

    private String ville;

    private String cite;

    private String indication;

    private Integer telephone;

    private Integer mobile;

    private LocalDate creeLe;

    private Integer creePar;

    private LocalDate modifieLe;

    private Integer modifiePar;


    private Long clientId;

    private Long zoneId;

    private Long codePostalId;

    private String codePostalCodePostal;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPrincipale() {
        return principale;
    }

    public void setPrincipale(Boolean principale) {
        this.principale = principale;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getAdresseLigne1() {
        return adresseLigne1;
    }

    public void setAdresseLigne1(Integer adresseLigne1) {
        this.adresseLigne1 = adresseLigne1;
    }

    public Integer getAdresseLigne2() {
        return adresseLigne2;
    }

    public void setAdresseLigne2(Integer adresseLigne2) {
        this.adresseLigne2 = adresseLigne2;
    }

    public String getGouvernorat() {
        return gouvernorat;
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCite() {
        return cite;
    }

    public void setCite(String cite) {
        this.cite = cite;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public Integer getCreePar() {
        return creePar;
    }

    public void setCreePar(Integer creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public Integer getModifiePar() {
        return modifiePar;
    }

    public void setModifiePar(Integer modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public Long getCodePostalId() {
        return codePostalId;
    }

    public void setCodePostalId(Long codePostauxId) {
        this.codePostalId = codePostauxId;
    }

    public String getCodePostalCodePostal() {
        return codePostalCodePostal;
    }

    public void setCodePostalCodePostal(String codePostauxCodePostal) {
        this.codePostalCodePostal = codePostauxCodePostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdresseDTO adresseDTO = (AdresseDTO) o;
        if (adresseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adresseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdresseDTO{" +
            "id=" + getId() +
            ", principale='" + isPrincipale() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", adresseLigne1=" + getAdresseLigne1() +
            ", adresseLigne2=" + getAdresseLigne2() +
            ", gouvernorat='" + getGouvernorat() + "'" +
            ", ville='" + getVille() + "'" +
            ", cite='" + getCite() + "'" +
            ", indication='" + getIndication() + "'" +
            ", telephone=" + getTelephone() +
            ", mobile=" + getMobile() +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar=" + getCreePar() +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar=" + getModifiePar() +
            ", clientId=" + getClientId() +
            ", zoneId=" + getZoneId() +
            ", codePostalId=" + getCodePostalId() +
            ", codePostalCodePostal='" + getCodePostalCodePostal() + "'" +
            "}";
    }
}
