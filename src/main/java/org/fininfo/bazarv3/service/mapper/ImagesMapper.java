package org.fininfo.bazarv3.service.mapper;


import org.fininfo.bazarv3.domain.*;
import org.fininfo.bazarv3.service.dto.ImagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Images} and its DTO {@link ImagesDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProduitMapper.class, CategorieMapper.class, SousCategorieMapper.class})
public interface ImagesMapper extends EntityMapper<ImagesDTO, Images> {

    @Mapping(source = "idProduit.id", target = "idProduitId")
    @Mapping(source = "idCategorie.id", target = "idCategorieId")
    @Mapping(source = "idSousCategorie.id", target = "idSousCategorieId")
    ImagesDTO toDto(Images images);

    @Mapping(source = "idProduitId", target = "idProduit")
    @Mapping(source = "idCategorieId", target = "idCategorie")
    @Mapping(source = "idSousCategorieId", target = "idSousCategorie")
    Images toEntity(ImagesDTO imagesDTO);

    default Images fromId(Long id) {
        if (id == null) {
            return null;
        }
        Images images = new Images();
        images.setId(id);
        return images;
    }
}
