package org.example.citronix.service.recolte;

import org.example.citronix.dto.RecolteDTO;
import org.example.citronix.dto.DetailRecolteDTO;

import java.util.List;

public interface RecolteService {

    RecolteDTO ajouterRecolte(RecolteDTO recolteDTO);
    List<RecolteDTO> getAllRecoltes();
    RecolteDTO getRecolteById(Long id);
    RecolteDTO updateRecolte(Long id, RecolteDTO recolteDTO);
    void supprimerRecolte(Long id);

}
