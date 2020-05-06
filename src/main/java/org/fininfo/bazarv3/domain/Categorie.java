package org.fininfo.bazarv3.domain;

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

/**
 * A Categorie.
 */
@Entity
@Table(name = "categorie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "categorie")
public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "etat")
    private Boolean etat;

    @Column(name = "cree_le")
    private LocalDate creeLe;

    @Column(name = "cree_par")
    private Integer creePar;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private Integer modifiePar;

    @OneToMany(mappedBy = "categorie")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SousCategorie> sousCategories = new HashSet<>();

    @OneToMany(mappedBy = "idCategorie")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Stock> stocks = new HashSet<>();

    @OneToMany(mappedBy = "idCategorie")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Images> images = new HashSet<>();

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

    public Categorie nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public Categorie description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPosition() {
        return position;
    }

    public Categorie position(Integer position) {
        this.position = position;
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean isEtat() {
        return etat;
    }

    public Categorie etat(Boolean etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public Categorie creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public Integer getCreePar() {
        return creePar;
    }

    public Categorie creePar(Integer creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(Integer creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public Categorie modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public Integer getModifiePar() {
        return modifiePar;
    }

    public Categorie modifiePar(Integer modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(Integer modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Set<SousCategorie> getSousCategories() {
        return sousCategories;
    }

    public Categorie sousCategories(Set<SousCategorie> sousCategories) {
        this.sousCategories = sousCategories;
        return this;
    }

    public Categorie addSousCategorie(SousCategorie sousCategorie) {
        this.sousCategories.add(sousCategorie);
        sousCategorie.setCategorie(this);
        return this;
    }

    public Categorie removeSousCategorie(SousCategorie sousCategorie) {
        this.sousCategories.remove(sousCategorie);
        sousCategorie.setCategorie(null);
        return this;
    }

    public void setSousCategories(Set<SousCategorie> sousCategories) {
        this.sousCategories = sousCategories;
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public Categorie stocks(Set<Stock> stocks) {
        this.stocks = stocks;
        return this;
    }

    public Categorie addStock(Stock stock) {
        this.stocks.add(stock);
        stock.setIdCategorie(this);
        return this;
    }

    public Categorie removeStock(Stock stock) {
        this.stocks.remove(stock);
        stock.setIdCategorie(null);
        return this;
    }

    public void setStocks(Set<Stock> stocks) {
        this.stocks = stocks;
    }

    public Set<Images> getImages() {
        return images;
    }

    public Categorie images(Set<Images> images) {
        this.images = images;
        return this;
    }

    public Categorie addImages(Images images) {
        this.images.add(images);
        images.setIdCategorie(this);
        return this;
    }

    public Categorie removeImages(Images images) {
        this.images.remove(images);
        images.setIdCategorie(null);
        return this;
    }

    public void setImages(Set<Images> images) {
        this.images = images;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categorie)) {
            return false;
        }
        return id != null && id.equals(((Categorie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Categorie{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", position=" + getPosition() +
            ", etat='" + isEtat() + "'" +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar=" + getCreePar() +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar=" + getModifiePar() +
            "}";
    }
}
