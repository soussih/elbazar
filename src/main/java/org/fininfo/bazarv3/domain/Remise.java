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
 * A Remise.
 */
@Entity
@Table(name = "remise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "remise")
public class Remise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "categorie_client")
    private CatClient categorieClient;

    @Column(name = "prix_unitaire")
    private Double prixUnitaire;

    @Column(name = "remise_categorie")
    private Double remiseCategorie;

    @Column(name = "remise_promo")
    private Double remisePromo;

    @Column(name = "cumulable")
    private Boolean cumulable;

    @Column(name = "debut_promo")
    private LocalDate debutPromo;

    @Column(name = "fin_promo")
    private LocalDate finPromo;

    @Column(name = "cree_le")
    private LocalDate creeLe;

    @Column(name = "cree_par")
    private Integer creePar;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private Integer modifiePar;

    @ManyToOne
    @JsonIgnoreProperties("remises")
    private Produit idProduit;

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

    public Remise categorieClient(CatClient categorieClient) {
        this.categorieClient = categorieClient;
        return this;
    }

    public void setCategorieClient(CatClient categorieClient) {
        this.categorieClient = categorieClient;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public Remise prixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
        return this;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Double getRemiseCategorie() {
        return remiseCategorie;
    }

    public Remise remiseCategorie(Double remiseCategorie) {
        this.remiseCategorie = remiseCategorie;
        return this;
    }

    public void setRemiseCategorie(Double remiseCategorie) {
        this.remiseCategorie = remiseCategorie;
    }

    public Double getRemisePromo() {
        return remisePromo;
    }

    public Remise remisePromo(Double remisePromo) {
        this.remisePromo = remisePromo;
        return this;
    }

    public void setRemisePromo(Double remisePromo) {
        this.remisePromo = remisePromo;
    }

    public Boolean isCumulable() {
        return cumulable;
    }

    public Remise cumulable(Boolean cumulable) {
        this.cumulable = cumulable;
        return this;
    }

    public void setCumulable(Boolean cumulable) {
        this.cumulable = cumulable;
    }

    public LocalDate getDebutPromo() {
        return debutPromo;
    }

    public Remise debutPromo(LocalDate debutPromo) {
        this.debutPromo = debutPromo;
        return this;
    }

    public void setDebutPromo(LocalDate debutPromo) {
        this.debutPromo = debutPromo;
    }

    public LocalDate getFinPromo() {
        return finPromo;
    }

    public Remise finPromo(LocalDate finPromo) {
        this.finPromo = finPromo;
        return this;
    }

    public void setFinPromo(LocalDate finPromo) {
        this.finPromo = finPromo;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public Remise creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public Integer getCreePar() {
        return creePar;
    }

    public Remise creePar(Integer creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(Integer creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public Remise modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public Integer getModifiePar() {
        return modifiePar;
    }

    public Remise modifiePar(Integer modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(Integer modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Produit getIdProduit() {
        return idProduit;
    }

    public Remise idProduit(Produit produit) {
        this.idProduit = produit;
        return this;
    }

    public void setIdProduit(Produit produit) {
        this.idProduit = produit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Remise)) {
            return false;
        }
        return id != null && id.equals(((Remise) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Remise{" +
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
            "}";
    }
}
