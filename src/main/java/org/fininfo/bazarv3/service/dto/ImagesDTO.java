package org.fininfo.bazarv3.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import org.fininfo.bazarv3.domain.enumeration.ImgType;

/**
 * A DTO for the {@link org.fininfo.bazarv3.domain.Images} entity.
 */
public class ImagesDTO implements Serializable {
    
    private Long id;

    private ImgType type;

    @Lob
    private byte[] image;

    private String imageContentType;

    private Long idProduitId;

    private Long idCategorieId;

    private Long idSousCategorieId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ImgType getType() {
        return type;
    }

    public void setType(ImgType type) {
        this.type = type;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Long getIdProduitId() {
        return idProduitId;
    }

    public void setIdProduitId(Long produitId) {
        this.idProduitId = produitId;
    }

    public Long getIdCategorieId() {
        return idCategorieId;
    }

    public void setIdCategorieId(Long categorieId) {
        this.idCategorieId = categorieId;
    }

    public Long getIdSousCategorieId() {
        return idSousCategorieId;
    }

    public void setIdSousCategorieId(Long sousCategorieId) {
        this.idSousCategorieId = sousCategorieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImagesDTO imagesDTO = (ImagesDTO) o;
        if (imagesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), imagesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImagesDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", image='" + getImage() + "'" +
            ", idProduitId=" + getIdProduitId() +
            ", idCategorieId=" + getIdCategorieId() +
            ", idSousCategorieId=" + getIdSousCategorieId() +
            "}";
    }
}
