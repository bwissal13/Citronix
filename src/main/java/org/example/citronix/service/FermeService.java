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
        // Validation des règles métier
        if (fermeDTO.getSuperficie() > 100) {
            throw new IllegalArgumentException("La superficie d'une ferme ne peut pas dépasser 100 hectares.");
        }

        Ferme ferme = FermeMapper.INSTANCE.toEntity(fermeDTO);
        ferme = fermeRepository.save(ferme);
        return FermeMapper.INSTANCE.toDTO(ferme);
    }
}
