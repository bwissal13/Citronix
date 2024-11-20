package org.example.citronix.service.arbre;

import org.example.citronix.dto.ArbreDTO;

import java.util.List;

public interface ArbreService {
    ArbreDTO ajouterArbre(ArbreDTO arbreDTO);

    List<ArbreDTO> getArbresByChamp(Long champId);

    ArbreDTO getArbreById(Long id);

    ArbreDTO modifierArbre(Long id, ArbreDTO arbreDTO);

    void supprimerArbre(Long id);
}
