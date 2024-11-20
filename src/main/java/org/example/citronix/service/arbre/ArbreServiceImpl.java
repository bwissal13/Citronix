package org.example.citronix.service.arbre;

import org.example.citronix.dto.ArbreDTO;
import org.example.citronix.entity.Arbre;
import org.example.citronix.entity.Champ;
import org.example.citronix.mapper.ArbreMapper;
import org.example.citronix.repository.ArbreRepository;
import org.example.citronix.repository.ChampRepository;
import org.example.citronix.service.arbre.ArbreService;
import org.example.citronix.utility.AgeCalculator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArbreServiceImpl implements ArbreService {
    private final ArbreRepository arbreRepository;
    private final ChampRepository champRepository;

    public ArbreServiceImpl(ArbreRepository arbreRepository, ChampRepository champRepository) {
        this.arbreRepository = arbreRepository;
        this.champRepository = champRepository;
    }
    @Override
    public ArbreDTO ajouterArbre(ArbreDTO arbreDTO) {
        // Fetch the associated Champ
        Champ champ = champRepository.findById(arbreDTO.getChampId())
                .orElseThrow(() -> new IllegalArgumentException("Champ non trouvé."));

        // Validate constraints
        validateArbreConstraints(champ);

        // Map and calculate age
        Arbre arbre = ArbreMapper.INSTANCE.toEntity(arbreDTO);
        arbre.setChamp(champ);
        arbre.setAge(AgeCalculator.calculateAge(arbre.getDatePlantation()));

        Arbre savedArbre = arbreRepository.save(arbre);
        return ArbreMapper.INSTANCE.toDTO(savedArbre);
    }

    @Override
    public ArbreDTO modifierArbre(Long id, ArbreDTO arbreDTO) {
        Arbre existingArbre = arbreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Arbre non trouvé."));

        Champ champ = champRepository.findById(arbreDTO.getChampId())
                .orElseThrow(() -> new IllegalArgumentException("Champ non trouvé."));

        validateArbreConstraints(champ);

        // Update fields and recalculate age
        existingArbre.setType(arbreDTO.getType());
        existingArbre.setDatePlantation(arbreDTO.getDatePlantation());
        existingArbre.setAge(AgeCalculator.calculateAge(arbreDTO.getDatePlantation()));
        existingArbre.setChamp(champ);

        Arbre updatedArbre = arbreRepository.save(existingArbre);
        return ArbreMapper.INSTANCE.toDTO(updatedArbre);
    }

    @Override
    public List<ArbreDTO> getArbresByChamp(Long champId) {
        return arbreRepository.findByChampId(champId).stream()
                .map(ArbreMapper.INSTANCE::toDTO)
                .toList();
    }

    @Override
    public ArbreDTO getArbreById(Long id) {
        Arbre arbre = arbreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Arbre non trouvé."));
        return ArbreMapper.INSTANCE.toDTO(arbre);
    }


    @Override
    public void supprimerArbre(Long id) {
        if (!arbreRepository.existsById(id)) {
            throw new IllegalArgumentException("Arbre non trouvé.");
        }
        arbreRepository.deleteById(id);
    }

    private void validateArbreConstraints(Champ champ) {
        int maxDensity = (int) (champ.getSuperficie() * 100); // 100 trees per hectare
        int currentTreeCount = champ.getArbres().size();

        if (currentTreeCount >= maxDensity) {
            throw new IllegalArgumentException("La densité maximale d'arbres pour ce champ est atteinte.");
        }
    }
}
