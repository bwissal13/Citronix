package org.example.citronix.mapper;

import org.example.citronix.dto.DetailRecolteDTO;
import org.example.citronix.entity.DetailRecolte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DetailRecolteMapper {

    DetailRecolteMapper INSTANCE = Mappers.getMapper(DetailRecolteMapper.class);
    @Mapping(source = "arbre.id", target = "arbreId")
    @Mapping(source = "recolte.id", target = "recolteId")

    DetailRecolteDTO toDTO(DetailRecolte detailRecolte);
    @Mapping(target = "quantite", source = "quantite")
    DetailRecolte toEntity(DetailRecolteDTO detailRecolteDTO);
}
