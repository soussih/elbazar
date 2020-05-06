package org.fininfo.bazarv3.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.fininfo.bazarv3.domain.Stock} entity.
 */
public class StockDTO implements Serializable {
    
    private Long id;

    private Double stockPhysique;

    private Double stockDisponible;

    private Double stockReserve;

    private Double stockCommande;

    private LocalDate derniereEntre;

    private LocalDate derniereSortie;

    private Boolean alerteStock;

    private LocalDate creeLe;

    private Integer creePar;

    private LocalDate modifieLe;

    private Integer modifiePar;


    private Long idProduitId;

    private Long idCategorieId;

    private Long idSousCategorieId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getStockPhysique() {
        return stockPhysique;
    }

    public void setStockPhysique(Double stockPhysique) {
        this.stockPhysique = stockPhysique;
    }

    public Double getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(Double stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public Double getStockReserve() {
        return stockReserve;
    }

    public void setStockReserve(Double stockReserve) {
        this.stockReserve = stockReserve;
    }

    public Double getStockCommande() {
        return stockCommande;
    }

    public void setStockCommande(Double stockCommande) {
        this.stockCommande = stockCommande;
    }

    public LocalDate getDerniereEntre() {
        return derniereEntre;
    }

    public void setDerniereEntre(LocalDate derniereEntre) {
        this.derniereEntre = derniereEntre;
    }

    public LocalDate getDerniereSortie() {
        return derniereSortie;
    }

    public void setDerniereSortie(LocalDate derniereSortie) {
        this.derniereSortie = derniereSortie;
    }

    public Boolean isAlerteStock() {
        return alerteStock;
    }

    public void setAlerteStock(Boolean alerteStock) {
        this.alerteStock = alerteStock;
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

    public Long getIdCategorieId() {
        return idCategorieId;
    }

    public void setIdCategorieId(Long categorieId) {
        this.idCategorieId = categorieId;
    }

    public Long getIdSousCategorieId() {
        return idSousCategorieId;
    }

    public void setIdSousCategorieId(Long sousCategorieId) {
        this.idSousCategorieId = sousCategorieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockDTO stockDTO = (StockDTO) o;
        if (stockDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockDTO{" +
            "id=" + getId() +
            ", stockPhysique=" + getStockPhysique() +
            ", stockDisponible=" + getStockDisponible() +
            ", stockReserve=" + getStockReserve() +
            ", stockCommande=" + getStockCommande() +
            ", derniereEntre='" + getDerniereEntre() + "'" +
            ", derniereSortie='" + getDerniereSortie() + "'" +
            ", alerteStock='" + isAlerteStock() + "'" +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar=" + getCreePar() +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar=" + getModifiePar() +
            ", idProduitId=" + getIdProduitId() +
            ", idCategorieId=" + getIdCategorieId() +
            ", idSousCategorieId=" + getIdSousCategorieId() +
            "}";
    }
}
