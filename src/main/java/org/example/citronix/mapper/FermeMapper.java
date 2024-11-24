package org.example.citronix.mapper;

import org.example.citronix.dto.FermeDTO;
import org.example.citronix.entity.Ferme;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FermeMapper {
    FermeMapper INSTANCE = Mappers.getMapper(FermeMapper.class);
    Ferme toEntity(FermeDTO fermeDTO);
    FermeDTO toDTO(Ferme ferme);
}
