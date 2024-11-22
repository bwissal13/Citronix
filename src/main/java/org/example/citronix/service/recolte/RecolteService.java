package org.example.citronix.service.recolte;

import org.example.citronix.dto.RecolteDTO;

import java.util.List;

public interface RecolteService {
    RecolteDTO ajouterRecolte(RecolteDTO recolteDTO);
    List<RecolteDTO> getRecoltesByChamp(Long champId);
    RecolteDTO getRecolteById(Long id);
//    RecolteDTO modifierRecolte(Long id, RecolteDTO recolteDTO);
//    void supprimerRecolte(Long id);
}
