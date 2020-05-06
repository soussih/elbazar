package org.fininfo.bazarv3.service.mapper;


import org.fininfo.bazarv3.domain.*;
import org.fininfo.bazarv3.service.dto.StockDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Stock} and its DTO {@link StockDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProduitMapper.class, CategorieMapper.class, SousCategorieMapper.class})
public interface StockMapper extends EntityMapper<StockDTO, Stock> {

    @Mapping(source = "idProduit.id", target = "idProduitId")
    @Mapping(source = "idCategorie.id", target = "idCategorieId")
    @Mapping(source = "idSousCategorie.id", target = "idSousCategorieId")
    StockDTO toDto(Stock stock);

    @Mapping(source = "idProduitId", target = "idProduit")
    @Mapping(source = "idCategorieId", target = "idCategorie")
    @Mapping(source = "idSousCategorieId", target = "idSousCategorie")
    Stock toEntity(StockDTO stockDTO);

    default Stock fromId(Long id) {
        if (id == null) {
            return null;
        }
        Stock stock = new Stock();
        stock.setId(id);
        return stock;
    }
}
