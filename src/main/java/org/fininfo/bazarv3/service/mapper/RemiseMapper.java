package org.fininfo.bazarv3.service.mapper;


import org.fininfo.bazarv3.domain.*;
import org.fininfo.bazarv3.service.dto.RemiseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Remise} and its DTO {@link RemiseDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProduitMapper.class})
public interface RemiseMapper extends EntityMapper<RemiseDTO, Remise> {

    @Mapping(source = "idProduit.id", target = "idProduitId")
    RemiseDTO toDto(Remise remise);

    @Mapping(source = "idProduitId", target = "idProduit")
    Remise toEntity(RemiseDTO remiseDTO);

    default Remise fromId(Long id) {
        if (id == null) {
            return null;
        }
        Remise remise = new Remise();
        remise.setId(id);
        return remise;
    }
}
