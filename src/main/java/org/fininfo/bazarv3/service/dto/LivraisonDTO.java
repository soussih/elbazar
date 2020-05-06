package org.fininfo.bazarv3.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import org.fininfo.bazarv3.domain.enumeration.CatClient;

/**
 * A DTO for the {@link org.fininfo.bazarv3.domain.Livraison} entity.
 */
public class LivraisonDTO implements Serializable {
    
    private Long id;

    private CatClient categorieClient;

    private Double intervalValeur;

    private Double frais;

    private LocalDate creeLe;

    private Integer creePar;

    private LocalDate modifieLe;

    private Integer modifiePar;


    private Long zoneId;

    private String zoneNom;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CatClient getCategorieClient() {
        return categorieClient;
    }

    public void setCategorieClient(CatClient categorieClient) {
        this.categorieClient = categorieClient;
    }

    public Double getIntervalValeur() {
        return intervalValeur;
    }

    public void setIntervalValeur(Double intervalValeur) {
        this.intervalValeur = intervalValeur;
    }

    public Double getFrais() {
        return frais;
    }

    public void setFrais(Double frais) {
        this.frais = frais;
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

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneNom() {
        return zoneNom;
    }

    public void setZoneNom(String zoneNom) {
        this.zoneNom = zoneNom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LivraisonDTO livraisonDTO = (LivraisonDTO) o;
        if (livraisonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), livraisonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LivraisonDTO{" +
            "id=" + getId() +
            ", categorieClient='" + getCategorieClient() + "'" +
            ", intervalValeur=" + getIntervalValeur() +
            ", frais=" + getFrais() +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar=" + getCreePar() +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar=" + getModifiePar() +
            ", zoneId=" + getZoneId() +
            ", zoneNom='" + getZoneNom() + "'" +
            "}";
    }
}
