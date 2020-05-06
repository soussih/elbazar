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

import org.fininfo.bazarv3.domain.enumeration.StatCmd;

import org.fininfo.bazarv3.domain.enumeration.NaturePiece;

/**
 * A Commande.
 */
@Entity
@Table(name = "commande")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "commande")
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,12}$")
    @Column(name = "reference")
    private String reference;

    @Column(name = "date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private StatCmd statut;

    @Enumerated(EnumType.STRING)
    @Column(name = "nature_piece")
    private NaturePiece naturePiece;

    @Column(name = "total_ht")
    private Double totalHT;

    @Column(name = "total_tva")
    private Double totalTVA;

    @Column(name = "total_remise")
    private Double totalRemise;

    @Column(name = "tot_ttc")
    private Double totTTC;

    @Column(name = "zone")
    private String zone;

    @Column(name = "date_livraison")
    private LocalDate dateLivraison;

    @Column(name = "cree_le")
    private LocalDate creeLe;

    @Column(name = "cree_par")
    private Integer creePar;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private Integer modifiePar;

    @OneToMany(mappedBy = "refCommande")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MouvementStock> mouvementStocks = new HashSet<>();

    @OneToMany(mappedBy = "commande")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommandeLignes> commandeLignes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("commandes")
    private Client idClient;

    @ManyToOne
    @JsonIgnoreProperties("commandes")
    private Adresse idAdresse;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public Commande reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getDate() {
        return date;
    }

    public Commande date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public StatCmd getStatut() {
        return statut;
    }

    public Commande statut(StatCmd statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(StatCmd statut) {
        this.statut = statut;
    }

    public NaturePiece getNaturePiece() {
        return naturePiece;
    }

    public Commande naturePiece(NaturePiece naturePiece) {
        this.naturePiece = naturePiece;
        return this;
    }

    public void setNaturePiece(NaturePiece naturePiece) {
        this.naturePiece = naturePiece;
    }

    public Double getTotalHT() {
        return totalHT;
    }

    public Commande totalHT(Double totalHT) {
        this.totalHT = totalHT;
        return this;
    }

    public void setTotalHT(Double totalHT) {
        this.totalHT = totalHT;
    }

    public Double getTotalTVA() {
        return totalTVA;
    }

    public Commande totalTVA(Double totalTVA) {
        this.totalTVA = totalTVA;
        return this;
    }

    public void setTotalTVA(Double totalTVA) {
        this.totalTVA = totalTVA;
    }

    public Double getTotalRemise() {
        return totalRemise;
    }

    public Commande totalRemise(Double totalRemise) {
        this.totalRemise = totalRemise;
        return this;
    }

    public void setTotalRemise(Double totalRemise) {
        this.totalRemise = totalRemise;
    }

    public Double getTotTTC() {
        return totTTC;
    }

    public Commande totTTC(Double totTTC) {
        this.totTTC = totTTC;
        return this;
    }

    public void setTotTTC(Double totTTC) {
        this.totTTC = totTTC;
    }

    public String getZone() {
        return zone;
    }

    public Commande zone(String zone) {
        this.zone = zone;
        return this;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public LocalDate getDateLivraison() {
        return dateLivraison;
    }

    public Commande dateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
        return this;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public Commande creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public Integer getCreePar() {
        return creePar;
    }

    public Commande creePar(Integer creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(Integer creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public Commande modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public Integer getModifiePar() {
        return modifiePar;
    }

    public Commande modifiePar(Integer modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(Integer modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Set<MouvementStock> getMouvementStocks() {
        return mouvementStocks;
    }

    public Commande mouvementStocks(Set<MouvementStock> mouvementStocks) {
        this.mouvementStocks = mouvementStocks;
        return this;
    }

    public Commande addMouvementStock(MouvementStock mouvementStock) {
        this.mouvementStocks.add(mouvementStock);
        mouvementStock.setRefCommande(this);
        return this;
    }

    public Commande removeMouvementStock(MouvementStock mouvementStock) {
        this.mouvementStocks.remove(mouvementStock);
        mouvementStock.setRefCommande(null);
        return this;
    }

    public void setMouvementStocks(Set<MouvementStock> mouvementStocks) {
        this.mouvementStocks = mouvementStocks;
    }

    public Set<CommandeLignes> getCommandeLignes() {
        return commandeLignes;
    }

    public Commande commandeLignes(Set<CommandeLignes> commandeLignes) {
        this.commandeLignes = commandeLignes;
        return this;
    }

    public Commande addCommandeLignes(CommandeLignes commandeLignes) {
        this.commandeLignes.add(commandeLignes);
        commandeLignes.setCommande(this);
        return this;
    }

    public Commande removeCommandeLignes(CommandeLignes commandeLignes) {
        this.commandeLignes.remove(commandeLignes);
        commandeLignes.setCommande(null);
        return this;
    }

    public void setCommandeLignes(Set<CommandeLignes> commandeLignes) {
        this.commandeLignes = commandeLignes;
    }

    public Client getIdClient() {
        return idClient;
    }

    public Commande idClient(Client client) {
        this.idClient = client;
        return this;
    }

    public void setIdClient(Client client) {
        this.idClient = client;
    }

    public Adresse getIdAdresse() {
        return idAdresse;
    }

    public Commande idAdresse(Adresse adresse) {
        this.idAdresse = adresse;
        return this;
    }

    public void setIdAdresse(Adresse adresse) {
        this.idAdresse = adresse;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commande)) {
            return false;
        }
        return id != null && id.equals(((Commande) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Commande{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", date='" + getDate() + "'" +
            ", statut='" + getStatut() + "'" +
            ", naturePiece='" + getNaturePiece() + "'" +
            ", totalHT=" + getTotalHT() +
            ", totalTVA=" + getTotalTVA() +
            ", totalRemise=" + getTotalRemise() +
            ", totTTC=" + getTotTTC() +
            ", zone='" + getZone() + "'" +
            ", dateLivraison='" + getDateLivraison() + "'" +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar=" + getCreePar() +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar=" + getModifiePar() +
            "}";
    }
}
