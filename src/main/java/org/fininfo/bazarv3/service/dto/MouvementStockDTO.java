package org.fininfo.bazarv3.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import org.fininfo.bazarv3.domain.enumeration.TypeMvt;

/**
 * A DTO for the {@link org.fininfo.bazarv3.domain.MouvementStock} entity.
 */
public class MouvementStockDTO implements Serializable {
    
    private Long id;

    private TypeMvt type;

    private LocalDate date;

    private Integer sens;

    private Double quantite;

    private LocalDate creeLe;

    private Integer creePar;

    private LocalDate modifieLe;

    private Integer modifiePar;


    private Long idProduitId;

    private Long refCommandeId;

    private String refCommandeReference;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeMvt getType() {
        return type;
    }

    public void setType(TypeMvt type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getSens() {
        return sens;
    }

    public void setSens(Integer sens) {
        this.sens = sens;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
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

    public Long getRefCommandeId() {
        return refCommandeId;
    }

    public void setRefCommandeId(Long commandeId) {
        this.refCommandeId = commandeId;
    }

    public String getRefCommandeReference() {
        return refCommandeReference;
    }

    public void setRefCommandeReference(String commandeReference) {
        this.refCommandeReference = commandeReference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MouvementStockDTO mouvementStockDTO = (MouvementStockDTO) o;
        if (mouvementStockDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mouvementStockDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MouvementStockDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", date='" + getDate() + "'" +
            ", sens=" + getSens() +
            ", quantite=" + getQuantite() +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar=" + getCreePar() +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar=" + getModifiePar() +
            ", idProduitId=" + getIdProduitId() +
            ", refCommandeId=" + getRefCommandeId() +
            ", refCommandeReference='" + getRefCommandeReference() + "'" +
            "}";
    }
}
