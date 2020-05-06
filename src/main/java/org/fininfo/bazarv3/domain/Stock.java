package org.fininfo.bazarv3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A Stock.
 */
@Entity
@Table(name = "stock")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "stock")
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "stock_physique")
    private Double stockPhysique;

    @Column(name = "stock_disponible")
    private Double stockDisponible;

    @Column(name = "stock_reserve")
    private Double stockReserve;

    @Column(name = "stock_commande")
    private Double stockCommande;

    @Column(name = "derniere_entre")
    private LocalDate derniereEntre;

    @Column(name = "derniere_sortie")
    private LocalDate derniereSortie;

    @Column(name = "alerte_stock")
    private Boolean alerteStock;

    @Column(name = "cree_le")
    private LocalDate creeLe;

    @Column(name = "cree_par")
    private Integer creePar;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private Integer modifiePar;

    @ManyToOne
    @JsonIgnoreProperties("stocks")
    private Produit idProduit;

    @ManyToOne
    @JsonIgnoreProperties("stocks")
    private Categorie idCategorie;

    @ManyToOne
    @JsonIgnoreProperties("stocks")
    private SousCategorie idSousCategorie;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getStockPhysique() {
        return stockPhysique;
    }

    public Stock stockPhysique(Double stockPhysique) {
        this.stockPhysique = stockPhysique;
        return this;
    }

    public void setStockPhysique(Double stockPhysique) {
        this.stockPhysique = stockPhysique;
    }

    public Double getStockDisponible() {
        return stockDisponible;
    }

    public Stock stockDisponible(Double stockDisponible) {
        this.stockDisponible = stockDisponible;
        return this;
    }

    public void setStockDisponible(Double stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public Double getStockReserve() {
        return stockReserve;
    }

    public Stock stockReserve(Double stockReserve) {
        this.stockReserve = stockReserve;
        return this;
    }

    public void setStockReserve(Double stockReserve) {
        this.stockReserve = stockReserve;
    }

    public Double getStockCommande() {
        return stockCommande;
    }

    public Stock stockCommande(Double stockCommande) {
        this.stockCommande = stockCommande;
        return this;
    }

    public void setStockCommande(Double stockCommande) {
        this.stockCommande = stockCommande;
    }

    public LocalDate getDerniereEntre() {
        return derniereEntre;
    }

    public Stock derniereEntre(LocalDate derniereEntre) {
        this.derniereEntre = derniereEntre;
        return this;
    }

    public void setDerniereEntre(LocalDate derniereEntre) {
        this.derniereEntre = derniereEntre;
    }

    public LocalDate getDerniereSortie() {
        return derniereSortie;
    }

    public Stock derniereSortie(LocalDate derniereSortie) {
        this.derniereSortie = derniereSortie;
        return this;
    }

    public void setDerniereSortie(LocalDate derniereSortie) {
        this.derniereSortie = derniereSortie;
    }

    public Boolean isAlerteStock() {
        return alerteStock;
    }

    public Stock alerteStock(Boolean alerteStock) {
        this.alerteStock = alerteStock;
        return this;
    }

    public void setAlerteStock(Boolean alerteStock) {
        this.alerteStock = alerteStock;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public Stock creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public Integer getCreePar() {
        return creePar;
    }

    public Stock creePar(Integer creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(Integer creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public Stock modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public Integer getModifiePar() {
        return modifiePar;
    }

    public Stock modifiePar(Integer modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(Integer modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Produit getIdProduit() {
        return idProduit;
    }

    public Stock idProduit(Produit produit) {
        this.idProduit = produit;
        return this;
    }

    public void setIdProduit(Produit produit) {
        this.idProduit = produit;
    }

    public Categorie getIdCategorie() {
        return idCategorie;
    }

    public Stock idCategorie(Categorie categorie) {
        this.idCategorie = categorie;
        return this;
    }

    public void setIdCategorie(Categorie categorie) {
        this.idCategorie = categorie;
    }

    public SousCategorie getIdSousCategorie() {
        return idSousCategorie;
    }

    public Stock idSousCategorie(SousCategorie sousCategorie) {
        this.idSousCategorie = sousCategorie;
        return this;
    }

    public void setIdSousCategorie(SousCategorie sousCategorie) {
        this.idSousCategorie = sousCategorie;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stock)) {
            return false;
        }
        return id != null && id.equals(((Stock) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Stock{" +
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
            "}";
    }
}
