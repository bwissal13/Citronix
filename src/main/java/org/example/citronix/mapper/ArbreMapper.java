package org.example.citronix.mapper;

import org.example.citronix.dto.ArbreDTO;
import org.example.citronix.entity.Arbre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.example.citronix.utility.AgeCalculator;
import org.example.citronix.utility.ProductivityCalculator;

@Mapper(componentModel = "spring")
public interface ArbreMapper {
    ArbreMapper INSTANCE = Mappers.getMapper(ArbreMapper.class);
    @Mapping(source = "champ.id", target = "champId")
    ArbreDTO toDTO(Arbre arbre);
    Arbre toEntity(ArbreDTO arbreDTO);
}
