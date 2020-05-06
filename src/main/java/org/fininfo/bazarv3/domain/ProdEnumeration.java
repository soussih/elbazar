package org.fininfo.bazarv3.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

import org.fininfo.bazarv3.domain.enumeration.ProdEnum;

/**
 * A ProdEnumeration.
 */
@Entity
@Table(name = "prod_enumeration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "prodenumeration")
public class ProdEnumeration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ProdEnum type;

    @Column(name = "nom")
    private String nom;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProdEnum getType() {
        return type;
    }

    public ProdEnumeration type(ProdEnum type) {
        this.type = type;
        return this;
    }

    public void setType(ProdEnum type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public ProdEnumeration nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProdEnumeration)) {
            return false;
        }
        return id != null && id.equals(((ProdEnumeration) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProdEnumeration{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
