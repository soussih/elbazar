package org.fininfo.bazarv3.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import org.fininfo.bazarv3.domain.enumeration.CatClient;

/**
 * A DTO for the {@link org.fininfo.bazarv3.domain.Remise} entity.
 */
public class RemiseDTO implements Serializable {
    
    private Long id;

    private CatClient categorieClient;

    private Double prixUnitaire;

    private Double remiseCategorie;

    private Double remisePromo;

    private Boolean cumulable;

    private LocalDate debutPromo;

    private LocalDate finPromo;

    private LocalDate creeLe;

    private Integer creePar;

    private LocalDate modifieLe;

    private Integer modifiePar;


    private Long idProduitId;
    
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

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Double getRemiseCategorie() {
        return remiseCategorie;
    }

    public void setRemiseCategorie(Double remiseCategorie) {
        this.remiseCategorie = remiseCategorie;
    }

    public Double getRemisePromo() {
        return remisePromo;
    }

    public void setRemisePromo(Double remisePromo) {
        this.remisePromo = remisePromo;
    }

    public Boolean isCumulable() {
        return cumulable;
    }

    public void setCumulable(Boolean cumulable) {
        this.cumulable = cumulable;
    }

    public LocalDate getDebutPromo() {
        return debutPromo;
    }

    public void setDebutPromo(LocalDate debutPromo) {
        this.debutPromo = debutPromo;
    }

    public LocalDate getFinPromo() {
        return finPromo;
    }

    public void setFinPromo(LocalDate finPromo) {
        this.finPromo = finPromo;
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

    public Long getIdProduitId() {
        return idProduitId;
    }

    public void setIdProduitId(Long produitId) {
        this.idProduitId = produitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RemiseDTO remiseDTO = (RemiseDTO) o;
        if (remiseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), remiseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RemiseDTO{" +
            "id=" + getId() +
            ", categorieClient='" + getCategorieClient() + "'" +
            ", prixUnitaire=" + getPrixUnitaire() +
            ", remiseCategorie=" + getRemiseCategorie() +
            ", remisePromo=" + getRemisePromo() +
            ", cumulable='" + isCumulable() + "'" +
            ", debutPromo='" + getDebutPromo() + "'" +
            ", finPromo='" + getFinPromo() + "'" +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar=" + getCreePar() +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar=" + getModifiePar() +
            ", idProduitId=" + getIdProduitId() +
            "}";
    }
}
