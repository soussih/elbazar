package org.fininfo.bazarv3.service.mapper;


import org.fininfo.bazarv3.domain.*;
import org.fininfo.bazarv3.service.dto.MouvementStockDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MouvementStock} and its DTO {@link MouvementStockDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProduitMapper.class, CommandeMapper.class})
public interface MouvementStockMapper extends EntityMapper<MouvementStockDTO, MouvementStock> {

    @Mapping(source = "idProduit.id", target = "idProduitId")
    @Mapping(source = "refCommande.id", target = "refCommandeId")
    @Mapping(source = "refCommande.reference", target = "refCommandeReference")
    MouvementStockDTO toDto(MouvementStock mouvementStock);

    @Mapping(source = "idProduitId", target = "idProduit")
    @Mapping(source = "refCommandeId", target = "refCommande")
    MouvementStock toEntity(MouvementStockDTO mouvementStockDTO);

    default MouvementStock fromId(Long id) {
        if (id == null) {
            return null;
        }
        MouvementStock mouvementStock = new MouvementStock();
        mouvementStock.setId(id);
        return mouvementStock;
    }
}
