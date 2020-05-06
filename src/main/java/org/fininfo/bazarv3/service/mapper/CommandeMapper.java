package org.fininfo.bazarv3.service.mapper;


import org.fininfo.bazarv3.domain.*;
import org.fininfo.bazarv3.service.dto.CommandeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commande} and its DTO {@link CommandeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class, AdresseMapper.class})
public interface CommandeMapper extends EntityMapper<CommandeDTO, Commande> {

    @Mapping(source = "idClient.id", target = "idClientId")
    @Mapping(source = "idAdresse.id", target = "idAdresseId")
    CommandeDTO toDto(Commande commande);

    @Mapping(target = "mouvementStocks", ignore = true)
    @Mapping(target = "removeMouvementStock", ignore = true)
    @Mapping(target = "commandeLignes", ignore = true)
    @Mapping(target = "removeCommandeLignes", ignore = true)
    @Mapping(source = "idClientId", target = "idClient")
    @Mapping(source = "idAdresseId", target = "idAdresse")
    Commande toEntity(CommandeDTO commandeDTO);

    default Commande fromId(Long id) {
        if (id == null) {
            return null;
        }
        Commande commande = new Commande();
        commande.setId(id);
        return commande;
    }
}
