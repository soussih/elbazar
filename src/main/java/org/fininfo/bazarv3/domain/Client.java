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

import org.fininfo.bazarv3.domain.enumeration.Civilite;

import org.fininfo.bazarv3.domain.enumeration.RegMod;

import org.fininfo.bazarv3.domain.enumeration.CatClient;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "civilite", nullable = false)
    private Civilite civilite;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "date_de_naissance", nullable = false)
    private LocalDate dateDeNaissance;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "mobile", nullable = false)
    private Integer mobile;

    @Enumerated(EnumType.STRING)
    @Column(name = "reglement")
    private RegMod reglement;

    @Column(name = "etat")
    private Boolean etat;

    @Column(name = "derniere_visite")
    private LocalDate derniereVisite;

    @Column(name = "total_achat")
    private Double totalAchat;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "categorie", nullable = false)
    private CatClient categorie;

    @Column(name = "points_fidelite")
    private Integer pointsFidelite;

    @Column(name = "cree_le")
    private LocalDate creeLe;

    @Column(name = "cree_par")
    private Integer creePar;

    @Column(name = "modifie_le")
    private LocalDate modifieLe;

    @Column(name = "modifie_par")
    private Integer modifiePar;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Adresse> adresses = new HashSet<>();

    @OneToMany(mappedBy = "idClient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Commande> commandes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("clients")
    private Adresse client;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Civilite getCivilite() {
        return civilite;
    }

    public Client civilite(Civilite civilite) {
        this.civilite = civilite;
        return this;
    }

    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }

    public String getNom() {
        return nom;
    }

    public Client nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Client prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    public Client dateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
        return this;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getEmail() {
        return email;
    }

    public Client email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getMobile() {
        return mobile;
    }

    public Client mobile(Integer mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public RegMod getReglement() {
        return reglement;
    }

    public Client reglement(RegMod reglement) {
        this.reglement = reglement;
        return this;
    }

    public void setReglement(RegMod reglement) {
        this.reglement = reglement;
    }

    public Boolean isEtat() {
        return etat;
    }

    public Client etat(Boolean etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public LocalDate getDerniereVisite() {
        return derniereVisite;
    }

    public Client derniereVisite(LocalDate derniereVisite) {
        this.derniereVisite = derniereVisite;
        return this;
    }

    public void setDerniereVisite(LocalDate derniereVisite) {
        this.derniereVisite = derniereVisite;
    }

    public Double getTotalAchat() {
        return totalAchat;
    }

    public Client totalAchat(Double totalAchat) {
        this.totalAchat = totalAchat;
        return this;
    }

    public void setTotalAchat(Double totalAchat) {
        this.totalAchat = totalAchat;
    }

    public CatClient getCategorie() {
        return categorie;
    }

    public Client categorie(CatClient categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(CatClient categorie) {
        this.categorie = categorie;
    }

    public Integer getPointsFidelite() {
        return pointsFidelite;
    }

    public Client pointsFidelite(Integer pointsFidelite) {
        this.pointsFidelite = pointsFidelite;
        return this;
    }

    public void setPointsFidelite(Integer pointsFidelite) {
        this.pointsFidelite = pointsFidelite;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public Client creeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
        return this;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public Integer getCreePar() {
        return creePar;
    }

    public Client creePar(Integer creePar) {
        this.creePar = creePar;
        return this;
    }

    public void setCreePar(Integer creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public Client modifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
        return this;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public Integer getModifiePar() {
        return modifiePar;
    }

    public Client modifiePar(Integer modifiePar) {
        this.modifiePar = modifiePar;
        return this;
    }

    public void setModifiePar(Integer modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Set<Adresse> getAdresses() {
        return adresses;
    }

    public Client adresses(Set<Adresse> adresses) {
        this.adresses = adresses;
        return this;
    }

    public Client addAdresse(Adresse adresse) {
        this.adresses.add(adresse);
        adresse.setClient(this);
        return this;
    }

    public Client removeAdresse(Adresse adresse) {
        this.adresses.remove(adresse);
        adresse.setClient(null);
        return this;
    }

    public void setAdresses(Set<Adresse> adresses) {
        this.adresses = adresses;
    }

    public Set<Commande> getCommandes() {
        return commandes;
    }

    public Client commandes(Set<Commande> commandes) {
        this.commandes = commandes;
        return this;
    }

    public Client addCommande(Commande commande) {
        this.commandes.add(commande);
        commande.setIdClient(this);
        return this;
    }

    public Client removeCommande(Commande commande) {
        this.commandes.remove(commande);
        commande.setIdClient(null);
        return this;
    }

    public void setCommandes(Set<Commande> commandes) {
        this.commandes = commandes;
    }

    public Adresse getClient() {
        return client;
    }

    public Client client(Adresse adresse) {
        this.client = adresse;
        return this;
    }

    public void setClient(Adresse adresse) {
        this.client = adresse;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", civilite='" + getCivilite() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            ", email='" + getEmail() + "'" +
            ", mobile=" + getMobile() +
            ", reglement='" + getReglement() + "'" +
            ", etat='" + isEtat() + "'" +
            ", derniereVisite='" + getDerniereVisite() + "'" +
            ", totalAchat=" + getTotalAchat() +
            ", categorie='" + getCategorie() + "'" +
            ", pointsFidelite=" + getPointsFidelite() +
            ", creeLe='" + getCreeLe() + "'" +
            ", creePar=" + getCreePar() +
            ", modifieLe='" + getModifieLe() + "'" +
            ", modifiePar=" + getModifiePar() +
            "}";
    }
}
