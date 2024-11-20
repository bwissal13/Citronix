package org.example.citronix.service;

import org.example.citronix.dto.FermeDTO;
import org.example.citronix.entity.Ferme;
import org.example.citronix.mapper.FermeMapper;
import org.example.citronix.repository.FermeRepository;
import org.springframework.stereotype.Service;

@Service
public class FermeService {
    private final FermeRepository fermeRepository;

    public FermeService(FermeRepository fermeRepository) {
        this.fermeRepository = fermeRepository;
    }

    public FermeDTO creerFerme(FermeDTO fermeDTO) {
        if (fermeDTO.getSuperficie() > 100) {
            throw new IllegalArgumentException("La superficie d'une ferme ne peut pas dépasser 100 hectares.");
        }
        Ferme ferme = FermeMapper.INSTANCE.toEntity(fermeDTO);
        ferme = fermeRepository.save(ferme);
        return FermeMapper.INSTANCE.toDTO(ferme);
    }
    public FermeDTO modifierFerme(Long fermeId, FermeDTO fermeDTO) {
        Ferme existingFerme = fermeRepository.findById(fermeId)
                .orElseThrow(() -> new IllegalArgumentException("Ferme non trouvée."));

        // Validation des contraintes métier
        if (fermeDTO.getSuperficie() <= 0.1) {
            throw new IllegalArgumentException("La superficie doit être supérieure à 0.1 hectare.");
        }

        // Mise à jour des champs modifiables
        existingFerme.setNom(fermeDTO.getNom());
        existingFerme.setLocalisation(fermeDTO.getLocalisation());
        existingFerme.setSuperficie(fermeDTO.getSuperficie());
        existingFerme.setDateCreation(fermeDTO.getDateCreation());

        // Enregistrement des modifications
        Ferme updatedFerme = fermeRepository.save(existingFerme);
        return FermeMapper.INSTANCE.toDTO(updatedFerme);
    }
    public FermeDTO consulterFerme(Long fermeId) {
        Ferme ferme = fermeRepository.findById(fermeId)
                .orElseThrow(() -> new IllegalArgumentException("Ferme non trouvée."));
        return FermeMapper.INSTANCE.toDTO(ferme);
    }
}
