package org.fininfo.bazarv3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

import org.fininfo.bazarv3.domain.enumeration.CatClient;

/**
 * A Livraison.
 */
@Entity
@Table(name = "livraison")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "livraison")
public class Livraison implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "categorie_client")
    private CatClient categorieClient;

    @Column(name = "interval_valeur")
    private Double intervalValeur;

    @Column(name = "frais")
    private Double frais;

    @Column(name = "cree_le")
    private LocalDate creeLe;

    @Column(name = "cree_par")
    private Integer creePar;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private Integer modifiePar;

    @ManyToOne
    @JsonIgnoreProperties("livraisons")
    private Zone zone;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CatClient getCategorieClient() {
        return categorieClient;
    }

    public Livraison categorieClient(CatClient categorieClient) {
        this.categorieClient = categorieClient;
        return this;
    }

    public void setCategorieClient(CatClient categorieClient) {
        this.categorieClient = categorieClient;
    }

    public Double getIntervalValeur() {
        return intervalValeur;
    }

    public Livraison intervalValeur(Double intervalValeur) {
        this.intervalValeur = intervalValeur;
        return this;
    }

    public void setIntervalValeur(Double intervalValeur) {
        this.intervalValeur = intervalValeur;
    }

    public Double getFrais() {
        return frais;
    }

    public Livraison frais(Double frais) {
        this.frais = frais;
        return this;
    }

    public void setFrais(Double frais) {
        this.frais = frais;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public Livraison creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public Integer getCreePar() {
        return creePar;
    }

    public Livraison creePar(Integer creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(Integer creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public Livraison modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public Integer getModifiePar() {
        return modifiePar;
    }

    public Livraison modifiePar(Integer modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(Integer modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Zone getZone() {
        return zone;
    }

    public Livraison zone(Zone zone) {
        this.zone = zone;
        return this;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Livraison)) {
            return false;
        }
        return id != null && id.equals(((Livraison) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Livraison{" +
            "id=" + getId() +
            ", categorieClient='" + getCategorieClient() + "'" +
            ", intervalValeur=" + getIntervalValeur() +
            ", frais=" + getFrais() +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar=" + getCreePar() +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar=" + getModifiePar() +
            "}";
    }
}
