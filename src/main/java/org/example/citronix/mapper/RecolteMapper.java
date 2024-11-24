package org.example.citronix.mapper;

import org.example.citronix.dto.RecolteDTO;
import org.example.citronix.entity.Recolte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface RecolteMapper {
    RecolteMapper INSTANCE = Mappers.getMapper(RecolteMapper.class);
    @Mapping(source = "champID", target = "champ.id")
    Recolte toEntity(RecolteDTO recolteDTO);
    RecolteDTO toDTO(Recolte recolte);
}
