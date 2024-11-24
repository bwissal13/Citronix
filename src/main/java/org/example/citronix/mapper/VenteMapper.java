package org.example.citronix.mapper;

import org.example.citronix.dto.VenteDTO;
import org.example.citronix.entity.Vente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VenteMapper {
    @Mapping(source = "total", target = "total")
    @Mapping(target = "recolte.id", source = "recolteId")
    Vente toEntity(VenteDTO dto);

    @Mapping(target = "recolteId", source = "recolte.id")
    VenteDTO toDTO(Vente entity);
}
