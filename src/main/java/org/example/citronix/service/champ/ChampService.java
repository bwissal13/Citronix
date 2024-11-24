package org.example.citronix.service.champ;

import org.example.citronix.dto.ChampDTO;

import java.util.List;

public interface ChampService {
    ChampDTO createChamp(ChampDTO champDTO);
    ChampDTO updateChamp(Long id, ChampDTO champDTO);
    void deleteChamp(Long id);
    ChampDTO getChampById(Long id);
    List<ChampDTO> getChampsByFerme(Long fermeId);
}
