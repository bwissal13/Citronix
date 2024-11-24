package org.example.citronix.service;

import org.example.citronix.dto.ChampDTO;
import org.example.citronix.dto.FermeDTO;
import org.example.citronix.entity.Champ;
import org.example.citronix.entity.Ferme;
import org.example.citronix.mapper.ChampMapper;
import org.example.citronix.mapper.FermeMapper;
import org.example.citronix.repository.ChampRepository;
import org.example.citronix.repository.FermeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FermeService {
    private final FermeRepository fermeRepository;
    private final ChampRepository champRepository;
    public FermeService(FermeRepository fermeRepository, ChampRepository champRepository) {
        this.fermeRepository = fermeRepository;
        this.champRepository = champRepository;
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
        if (fermeDTO.getSuperficie() <= 0.1) {
            throw new IllegalArgumentException("La superficie doit être supérieure à 0.1 hectare.");
        }
        existingFerme.setNom(fermeDTO.getNom());
        existingFerme.setLocalisation(fermeDTO.getLocalisation());
        existingFerme.setSuperficie(fermeDTO.getSuperficie());
        existingFerme.setDateCreation(fermeDTO.getDateCreation());
        Ferme updatedFerme = fermeRepository.save(existingFerme);
        return FermeMapper.INSTANCE.toDTO(updatedFerme);
    }
    public FermeDTO associerChamps(Long fermeId, List<ChampDTO> champsDTO) {
        Ferme ferme = fermeRepository.findById(fermeId)
                .orElseThrow(() -> new IllegalArgumentException("Ferme non trouvée."));
        if (ferme.getChamps().size() + champsDTO.size() > 10) {
            throw new IllegalArgumentException("Une ferme ne peut pas contenir plus de 10 champs.");
        }
        double superficieActuelle = ferme.getChamps().stream()
                .mapToDouble(Champ::getSuperficie)
                .sum();

        double nouvelleSuperficie = champsDTO.stream()
                .mapToDouble(ChampDTO::getSuperficie)
                .sum();

        if (superficieActuelle + nouvelleSuperficie >= ferme.getSuperficie()) {
            throw new IllegalArgumentException("La somme des superficies des champs doit être strictement inférieure à celle de la ferme.");
        }
        List<Champ> champs = champsDTO.stream()
                .map(ChampMapper.INSTANCE::toEntity)
                .toList();

        champs.forEach(champ -> champ.setFerme(ferme));
        champRepository.saveAll(champs);

        ferme.getChamps().addAll(champs);
        return FermeMapper.INSTANCE.toDTO(ferme);
    }
}
