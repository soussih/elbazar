package org.fininfo.bazarv3.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProduitUnite.
 */
@Entity
@Table(name = "produit_unite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "produitunite")
public class ProduitUnite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "prdunite")
    private String prdunite;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrdunite() {
        return prdunite;
    }

    public ProduitUnite prdunite(String prdunite) {
        this.prdunite = prdunite;
        return this;
    }

    public void setPrdunite(String prdunite) {
        this.prdunite = prdunite;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitUnite)) {
            return false;
        }
        return id != null && id.equals(((ProduitUnite) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProduitUnite{" +
            "id=" + getId() +
            ", prdunite='" + getPrdunite() + "'" +
            "}";
    }
}
