package org.fininfo.bazarv3.service.mapper;


import org.fininfo.bazarv3.domain.*;
import org.fininfo.bazarv3.service.dto.CommandeLignesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommandeLignes} and its DTO {@link CommandeLignesDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommandeMapper.class, ProduitMapper.class})
public interface CommandeLignesMapper extends EntityMapper<CommandeLignesDTO, CommandeLignes> {

    @Mapping(source = "commande.id", target = "commandeId")
    @Mapping(source = "idProduit.id", target = "idProduitId")
    CommandeLignesDTO toDto(CommandeLignes commandeLignes);

    @Mapping(source = "commandeId", target = "commande")
    @Mapping(source = "idProduitId", target = "idProduit")
    CommandeLignes toEntity(CommandeLignesDTO commandeLignesDTO);

    default CommandeLignes fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommandeLignes commandeLignes = new CommandeLignes();
        commandeLignes.setId(id);
        return commandeLignes;
    }
}
