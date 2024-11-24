package org.example.citronix.service.detailRecolte;

import org.example.citronix.dto.DetailRecolteDTO;
import org.example.citronix.dto.RecolteDTO;
import org.example.citronix.entity.Arbre;

import java.util.List;

public interface DetailRecolteService {

    List<DetailRecolteDTO> ajouterDetailsRecolte(DetailRecolteDTO  recolteDTO);
    double calculerQuantiteRecoltee(Arbre arbre);
}
