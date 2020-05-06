package org.fininfo.bazarv3.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.fininfo.bazarv3.domain.enumeration.StatCmd;
import org.fininfo.bazarv3.domain.enumeration.NaturePiece;

/**
 * A DTO for the {@link org.fininfo.bazarv3.domain.Commande} entity.
 */
public class CommandeDTO implements Serializable {
    
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9]{0,12}$")
    private String reference;

    private LocalDate date;

    private StatCmd statut;

    private NaturePiece naturePiece;

    private Double totalHT;

    private Double totalTVA;

    private Double totalRemise;

    private Double totTTC;

    private String zone;

    private LocalDate dateLivraison;

    private LocalDate creeLe;

    private Integer creePar;

    private LocalDate modifieLe;

    private Integer modifiePar;


    private Long idClientId;

    private Long idAdresseId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public StatCmd getStatut() {
        return statut;
    }

    public void setStatut(StatCmd statut) {
        this.statut = statut;
    }

    public NaturePiece getNaturePiece() {
        return naturePiece;
    }

    public void setNaturePiece(NaturePiece naturePiece) {
        this.naturePiece = naturePiece;
    }

    public Double getTotalHT() {
        return totalHT;
    }

    public void setTotalHT(Double totalHT) {
        this.totalHT = totalHT;
    }

    public Double getTotalTVA() {
        return totalTVA;
    }

    public void setTotalTVA(Double totalTVA) {
        this.totalTVA = totalTVA;
    }

    public Double getTotalRemise() {
        return totalRemise;
    }

    public void setTotalRemise(Double totalRemise) {
        this.totalRemise = totalRemise;
    }

    public Double getTotTTC() {
        return totTTC;
    }

    public void setTotTTC(Double totTTC) {
        this.totTTC = totTTC;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public LocalDate getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(LocalDate dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public LocalDate getCreeLe() {
        return creeLe;
    }

    public void setCreeLe(LocalDate creeLe) {
        this.creeLe = creeLe;
    }

    public Integer getCreePar() {
        return creePar;
    }

    public void setCreePar(Integer creePar) {
        this.creePar = creePar;
    }

    public LocalDate getModifieLe() {
        return modifieLe;
    }

    public void setModifieLe(LocalDate modifieLe) {
        this.modifieLe = modifieLe;
    }

    public Integer getModifiePar() {
        return modifiePar;
    }

    public void setModifiePar(Integer modifiePar) {
        this.modifiePar = modifiePar;
    }

    public Long getIdClientId() {
        return idClientId;
    }

    public void setIdClientId(Long clientId) {
        this.idClientId = clientId;
    }

    public Long getIdAdresseId() {
        return idAdresseId;
    }

    public void setIdAdresseId(Long adresseId) {
        this.idAdresseId = adresseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommandeDTO commandeDTO = (CommandeDTO) o;
        if (commandeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commandeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommandeDTO{" +
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
            ", idClientId=" + getIdClientId() +
            ", idAdresseId=" + getIdAdresseId() +
            "}";
    }
}
