package org.example.citronix.service.impl;

import org.example.citronix.dto.ChampDTO;
import org.example.citronix.entity.Champ;
import org.example.citronix.entity.Ferme;
import org.example.citronix.mapper.ChampMapper;
import org.example.citronix.repository.ChampRepository;
import org.example.citronix.repository.FermeRepository;
import org.example.citronix.service.ChampService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChampServiceImpl implements ChampService {
    private final ChampRepository champRepository;
    private final FermeRepository fermeRepository;

    public ChampServiceImpl(ChampRepository champRepository, FermeRepository fermeRepository) {
        this.champRepository = champRepository;
        this.fermeRepository = fermeRepository;
    }

    @Override
    public ChampDTO createChamp(ChampDTO champDTO) {
        Ferme ferme = fermeRepository.findById(champDTO.getFermeId())
                .orElseThrow(() -> new IllegalArgumentException("Ferme non trouvée."));

        validateChampConstraints(ferme, champDTO);

        Champ champ = ChampMapper.INSTANCE.toEntity(champDTO);
        champ.setFerme(ferme);

        Champ savedChamp = champRepository.save(champ);
        return ChampMapper.INSTANCE.toDTO(savedChamp);
    }

    @Override
    public ChampDTO updateChamp(Long id, ChampDTO champDTO) {
        Champ existingChamp = champRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Champ non trouvé."));

        Ferme ferme = fermeRepository.findById(champDTO.getFermeId())
                .orElseThrow(() -> new IllegalArgumentException("Ferme non trouvée."));

        validateChampConstraints(ferme, champDTO);

        existingChamp.setNom(champDTO.getNom());
        existingChamp.setSuperficie(champDTO.getSuperficie());
        existingChamp.setFerme(ferme);

        Champ updatedChamp = champRepository.save(existingChamp);
        return ChampMapper.INSTANCE.toDTO(updatedChamp);
    }

    @Override
    public void deleteChamp(Long id) {
        if (!champRepository.existsById(id)) {
            throw new IllegalArgumentException("Champ non trouvé.");
        }
        champRepository.deleteById(id);
    }

    @Override
    public ChampDTO getChampById(Long id) {
        Champ champ = champRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Champ non trouvé."));
        return ChampMapper.INSTANCE.toDTO(champ);
    }

    @Override
    public List<ChampDTO> getChampsByFerme(Long fermeId) {
        return champRepository.findByFermeId(fermeId).stream()
                .map(ChampMapper.INSTANCE::toDTO)
                .toList();
    }

    private void validateChampConstraints(Ferme ferme, ChampDTO champDTO) {
        // Validate superficie constraints
        double totalSuperficie = ferme.getChamps().stream()
                .mapToDouble(Champ::getSuperficie)
                .sum();
        if (totalSuperficie + champDTO.getSuperficie() > ferme.getSuperficie()) {
            throw new IllegalArgumentException("La superficie totale des champs dépasse celle de la ferme.");
        }

        // Validate max number of champs
        if (ferme.getChamps().size() >= 10) {
            throw new IllegalArgumentException("Une ferme ne peut pas contenir plus de 10 champs.");
        }
    }
}
