package org.fininfo.bazarv3.service.mapper;


import org.fininfo.bazarv3.domain.*;
import org.fininfo.bazarv3.service.dto.SousCategorieDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SousCategorie} and its DTO {@link SousCategorieDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategorieMapper.class})
public interface SousCategorieMapper extends EntityMapper<SousCategorieDTO, SousCategorie> {

    @Mapping(source = "categorie.id", target = "categorieId")
    @Mapping(source = "categorie.nom", target = "categorieNom")
    SousCategorieDTO toDto(SousCategorie sousCategorie);

    @Mapping(target = "produits", ignore = true)
    @Mapping(target = "removeProduit", ignore = true)
    @Mapping(target = "stocks", ignore = true)
    @Mapping(target = "removeStock", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "removeImages", ignore = true)
    @Mapping(source = "categorieId", target = "categorie")
    SousCategorie toEntity(SousCategorieDTO sousCategorieDTO);

    default SousCategorie fromId(Long id) {
        if (id == null) {
            return null;
        }
        SousCategorie sousCategorie = new SousCategorie();
        sousCategorie.setId(id);
        return sousCategorie;
    }
}
