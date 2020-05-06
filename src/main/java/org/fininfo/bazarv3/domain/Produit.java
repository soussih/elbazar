package org.fininfo.bazarv3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.fininfo.bazarv3.domain.enumeration.SourcePrd;

/**
 * A Produit.
 */
@Entity
@Table(name = "produit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "produit")
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "code_barre")
    private Integer codeBarre;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "etat", nullable = false)
    private Boolean etat;

    @Column(name = "unite")
    private String unite;

    @Column(name = "marque")
    private String marque;

    @Column(name = "nature")
    private String nature;

    @Column(name = "stock_minimum")
    private Double stockMinimum;

    @Column(name = "quantite_vente_max")
    private Double quantiteVenteMax;

    @Column(name = "date_premption")
    private LocalDate datePremption;

    @Column(name = "prix_unitaire_ht")
    private Double prixUnitaireHT;

    @Column(name = "taux_tva")
    private Double tauxTva;

    @Column(name = "prix_ttc")
    private Double prixTtc;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "source_produit", nullable = false)
    private SourcePrd sourceProduit;

    @Column(name = "hors_stock")
    private Boolean horsStock;

    @Pattern(regexp = "^[1-5]$")
    @Column(name = "rating")
    private String rating;

    @Column(name = "remise")
    private Double remise;

    @Column(name = "cree_le")
    private LocalDate creeLe;

    @Column(name = "cree_par")
    private Integer creePar;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private Integer modifiePar;

    @OneToMany(mappedBy = "idProduit")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Stock> stocks = new HashSet<>();

    @OneToMany(mappedBy = "idProduit")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MouvementStock> mouvementStocks = new HashSet<>();

    @OneToMany(mappedBy = "idProduit")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommandeLignes> commandeLignes = new HashSet<>();

    @OneToMany(mappedBy = "idProduit")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Remise> remises = new HashSet<>();

    @OneToMany(mappedBy = "idProduit")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Images> images = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("produits")
    private SousCategorie sousCategorie;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Produit nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getCodeBarre() {
        return codeBarre;
    }

    public Produit codeBarre(Integer codeBarre) {
        this.codeBarre = codeBarre;
        return this;
    }

    public void setCodeBarre(Integer codeBarre) {
        this.codeBarre = codeBarre;
    }

    public String getDescription() {
        return description;
    }

    public Produit description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isEtat() {
        return etat;
    }

    public Produit etat(Boolean etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public String getUnite() {
        return unite;
    }

    public Produit unite(String unite) {
        this.unite = unite;
        return this;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getMarque() {
        return marque;
    }

    public Produit marque(String marque) {
        this.marque = marque;
        return this;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getNature() {
        return nature;
    }

    public Produit nature(String nature) {
        this.nature = nature;
        return this;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public Double getStockMinimum() {
        return stockMinimum;
    }

    public Produit stockMinimum(Double stockMinimum) {
        this.stockMinimum = stockMinimum;
        return this;
    }

    public void setStockMinimum(Double stockMinimum) {
        this.stockMinimum = stockMinimum;
    }

    public Double getQuantiteVenteMax() {
        return quantiteVenteMax;
    }

    public Produit quantiteVenteMax(Double quantiteVenteMax) {
        this.quantiteVenteMax = quantiteVenteMax;
        return this;
    }

    public void setQuantiteVenteMax(Double quantiteVenteMax) {
        this.quantiteVenteMax = quantiteVenteMax;
    }

    public LocalDate getDatePremption() {
        return datePremption;
    }

    public Produit datePremption(LocalDate datePremption) {
        this.datePremption = datePremption;
        return this;
    }

    public void setDatePremption(LocalDate datePremption) {
        this.datePremption = datePremption;
    }

    public Double getPrixUnitaireHT() {
        return prixUnitaireHT;
    }

    public Produit prixUnitaireHT(Double prixUnitaireHT) {
        this.prixUnitaireHT = prixUnitaireHT;
        return this;
    }

    public void setPrixUnitaireHT(Double prixUnitaireHT) {
        this.prixUnitaireHT = prixUnitaireHT;
    }

    public Double getTauxTva() {
        return tauxTva;
    }

    public Produit tauxTva(Double tauxTva) {
        this.tauxTva = tauxTva;
        return this;
    }

    public void setTauxTva(Double tauxTva) {
        this.tauxTva = tauxTva;
    }

    public Double getPrixTtc() {
        return prixTtc;
    }

    public Produit prixTtc(Double prixTtc) {
        this.prixTtc = prixTtc;
        return this;
    }

    public void setPrixTtc(Double prixTtc) {
        this.prixTtc = prixTtc;
    }

    public SourcePrd getSourceProduit() {
        return sourceProduit;
    }

    public Produit sourceProduit(SourcePrd sourceProduit) {
        this.sourceProduit = sourceProduit;
        return this;
    }

    public void setSourceProduit(SourcePrd sourceProduit) {
        this.sourceProduit = sourceProduit;
    }

    public Boolean isHorsStock() {
        return horsStock;
    }

    public Produit horsStock(Boolean horsStock) {
        this.horsStock = horsStock;
        return this;
    }

    public void setHorsStock(Boolean horsStock) {
        this.horsStock = horsStock;
    }

    public String getRating() {
        return rating;
    }

    public Produit rating(String rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Double getRemise() {
        return remise;
    }

    public Produit remise(Double remise) {
        this.remise = remise;
        return this;
    }

    public void setRemise(Double remise) {
        this.remise = remise;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public Produit creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public Integer getCreePar() {
        return creePar;
    }

    public Produit creePar(Integer creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(Integer creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public Produit modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public Integer getModifiePar() {
        return modifiePar;
    }

    public Produit modifiePar(Integer modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(Integer modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public Produit stocks(Set<Stock> stocks) {
        this.stocks = stocks;
        return this;
    }

    public Produit addStock(Stock stock) {
        this.stocks.add(stock);
        stock.setIdProduit(this);
        return this;
    }

    public Produit removeStock(Stock stock) {
        this.stocks.remove(stock);
        stock.setIdProduit(null);
        return this;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    public Set<MouvementStock> getMouvementStocks() {
        return mouvementStocks;
    }

    public Produit mouvementStocks(Set<MouvementStock> mouvementStocks) {
        this.mouvementStocks = mouvementStocks;
        return this;
    }

    public Produit addMouvementStock(MouvementStock mouvementStock) {
        this.mouvementStocks.add(mouvementStock);
        mouvementStock.setIdProduit(this);
        return this;
    }

    public Produit removeMouvementStock(MouvementStock mouvementStock) {
        this.mouvementStocks.remove(mouvementStock);
        mouvementStock.setIdProduit(null);
        return this;
    }

    public void setMouvementStocks(Set<MouvementStock> mouvementStocks) {
        this.mouvementStocks = mouvementStocks;
    }

    public Set<CommandeLignes> getCommandeLignes() {
        return commandeLignes;
    }

    public Produit commandeLignes(Set<CommandeLignes> commandeLignes) {
        this.commandeLignes = commandeLignes;
        return this;
    }

    public Produit addCommandeLignes(CommandeLignes commandeLignes) {
        this.commandeLignes.add(commandeLignes);
        commandeLignes.setIdProduit(this);
        return this;
    }

    public Produit removeCommandeLignes(CommandeLignes commandeLignes) {
        this.commandeLignes.remove(commandeLignes);
        commandeLignes.setIdProduit(null);
        return this;
    }

    public void setCommandeLignes(Set<CommandeLignes> commandeLignes) {
        this.commandeLignes = commandeLignes;
    }

    public Set<Remise> getRemises() {
        return remises;
    }

    public Produit remises(Set<Remise> remises) {
        this.remises = remises;
        return this;
    }

    public Produit addRemise(Remise remise) {
        this.remises.add(remise);
        remise.setIdProduit(this);
        return this;
    }

    public Produit removeRemise(Remise remise) {
        this.remises.remove(remise);
        remise.setIdProduit(null);
        return this;
    }

    public void setRemises(Set<Remise> remises) {
        this.remises = remises;
    }

    public Set<Images> getImages() {
        return images;
    }

    public Produit images(Set<Images> images) {
        this.images = images;
        return this;
    }

    public Produit addImages(Images images) {
        this.images.add(images);
        images.setIdProduit(this);
        return this;
    }

    public Produit removeImages(Images images) {
        this.images.remove(images);
        images.setIdProduit(null);
        return this;
    }

    public void setImages(Set<Images> images) {
        this.images = images;
    }

    public SousCategorie getSousCategorie() {
        return sousCategorie;
    }

    public Produit sousCategorie(SousCategorie sousCategorie) {
        this.sousCategorie = sousCategorie;
        return this;
    }

    public void setSousCategorie(SousCategorie sousCategorie) {
        this.sousCategorie = sousCategorie;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produit)) {
            return false;
        }
        return id != null && id.equals(((Produit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Produit{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", codeBarre=" + getCodeBarre() +
            ", description='" + getDescription() + "'" +
            ", etat='" + isEtat() + "'" +
            ", unite='" + getUnite() + "'" +
            ", marque='" + getMarque() + "'" +
            ", nature='" + getNature() + "'" +
            ", stockMinimum=" + getStockMinimum() +
            ", quantiteVenteMax=" + getQuantiteVenteMax() +
            ", datePremption='" + getDatePremption() + "'" +
            ", prixUnitaireHT=" + getPrixUnitaireHT() +
            ", tauxTva=" + getTauxTva() +
            ", prixTtc=" + getPrixTtc() +
            ", sourceProduit='" + getSourceProduit() + "'" +
            ", horsStock='" + isHorsStock() + "'" +
            ", rating='" + getRating() + "'" +
            ", remise=" + getRemise() +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar=" + getCreePar() +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar=" + getModifiePar() +
            "}";
    }
}
