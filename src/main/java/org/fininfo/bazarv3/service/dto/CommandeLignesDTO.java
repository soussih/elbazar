package org.fininfo.bazarv3.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.fininfo.bazarv3.domain.CommandeLignes} entity.
 */
public class CommandeLignesDTO implements Serializable {
    
    private Long id;

    private Double quantite;

    private Double prixHT;

    private Double tva;

    private Double prixTTC;

    private LocalDate creeLe;

    private Integer creePar;

    private LocalDate modifieLe;

    private Integer modifiePar;


    private Long commandeId;

    private Long idProduitId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(Double prixHT) {
        this.prixHT = prixHT;
    }

    public Double getTva() {
        return tva;
    }

    public void setTva(Double tva) {
        this.tva = tva;
    }

    public Double getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(Double prixTTC) {
        this.prixTTC = prixTTC;
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

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
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

        CommandeLignesDTO commandeLignesDTO = (CommandeLignesDTO) o;
        if (commandeLignesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commandeLignesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommandeLignesDTO{" +
            "id=" + getId() +
            ", quantite=" + getQuantite() +
            ", prixHT=" + getPrixHT() +
            ", tva=" + getTva() +
            ", prixTTC=" + getPrixTTC() +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar=" + getCreePar() +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar=" + getModifiePar() +
            ", commandeId=" + getCommandeId() +
            ", idProduitId=" + getIdProduitId() +
            "}";
    }
}
