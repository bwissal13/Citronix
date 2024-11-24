package org.example.citronix.mapper;

import org.example.citronix.dto.ChampDTO;
import org.example.citronix.entity.Champ;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChampMapper {
    ChampMapper INSTANCE = Mappers.getMapper(ChampMapper.class);
    Champ toEntity(ChampDTO champDTO);
    ChampDTO toDTO(Champ champ);
}
