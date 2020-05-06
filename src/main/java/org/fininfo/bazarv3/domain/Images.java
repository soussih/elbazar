package org.fininfo.bazarv3.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

import org.fininfo.bazarv3.domain.enumeration.ImgType;

/**
 * A Images.
 */
@Entity
@Table(name = "images")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "images")
public class Images implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ImgType type;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @ManyToOne
    @JsonIgnoreProperties("images")
    private Produit idProduit;

    @ManyToOne
    @JsonIgnoreProperties("images")
    private Categorie idCategorie;

    @ManyToOne
    @JsonIgnoreProperties("images")
    private SousCategorie idSousCategorie;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ImgType getType() {
        return type;
    }

    public Images type(ImgType type) {
        this.type = type;
        return this;
    }

    public void setType(ImgType type) {
        this.type = type;
    }

    public byte[] getImage() {
        return image;
    }

    public Images image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Images imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Produit getIdProduit() {
        return idProduit;
    }

    public Images idProduit(Produit produit) {
        this.idProduit = produit;
        return this;
    }

    public void setIdProduit(Produit produit) {
        this.idProduit = produit;
    }

    public Categorie getIdCategorie() {
        return idCategorie;
    }

    public Images idCategorie(Categorie categorie) {
        this.idCategorie = categorie;
        return this;
    }

    public void setIdCategorie(Categorie categorie) {
        this.idCategorie = categorie;
    }

    public SousCategorie getIdSousCategorie() {
        return idSousCategorie;
    }

    public Images idSousCategorie(SousCategorie sousCategorie) {
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
        if (!(o instanceof Images)) {
            return false;
        }
        return id != null && id.equals(((Images) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Images{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
