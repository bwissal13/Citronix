package org.example.citronix.service.vente;

import org.example.citronix.dto.VenteDTO;

import java.util.List;

public interface VenteService {


    VenteDTO enregistrerVente(VenteDTO venteDTO);

    List<VenteDTO> getAllVentes();

    List<VenteDTO> getVentesByRecolteId(Long recolteId);

    void supprimerVente(Long id);
}
