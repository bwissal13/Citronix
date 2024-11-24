package org.example.citronix.mapper;

import org.example.citronix.dto.ArbreDTO;
import org.example.citronix.entity.Arbre;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArbreMapper {
    ArbreMapper INSTANCE = Mappers.getMapper(ArbreMapper.class);

    Arbre toEntity(ArbreDTO arbreDTO);

    ArbreDTO toDTO(Arbre arbre);
}
