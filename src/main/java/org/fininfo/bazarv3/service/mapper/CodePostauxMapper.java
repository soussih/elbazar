package org.fininfo.bazarv3.service.mapper;


import org.fininfo.bazarv3.domain.*;
import org.fininfo.bazarv3.service.dto.CodePostauxDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CodePostaux} and its DTO {@link CodePostauxDTO}.
 */
@Mapper(componentModel = "spring", uses = {ZoneMapper.class})
public interface CodePostauxMapper extends EntityMapper<CodePostauxDTO, CodePostaux> {

    @Mapping(source = "zone.id", target = "zoneId")
    @Mapping(source = "zone.nom", target = "zoneNom")
    CodePostauxDTO toDto(CodePostaux codePostaux);

    @Mapping(target = "adresses", ignore = true)
    @Mapping(target = "removeAdresse", ignore = true)
    @Mapping(source = "zoneId", target = "zone")
    CodePostaux toEntity(CodePostauxDTO codePostauxDTO);

    default CodePostaux fromId(Long id) {
        if (id == null) {
            return null;
        }
        CodePostaux codePostaux = new CodePostaux();
        codePostaux.setId(id);
        return codePostaux;
    }
}
