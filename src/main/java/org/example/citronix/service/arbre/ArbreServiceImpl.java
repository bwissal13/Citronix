package org.example.citronix.service.arbre;

import org.example.citronix.dto.ArbreDTO;
import org.example.citronix.entity.Arbre;
import org.example.citronix.entity.Champ;
import org.example.citronix.mapper.ArbreMapper;
import org.example.citronix.repository.ArbreRepository;
import org.example.citronix.repository.ChampRepository;
import org.example.citronix.utility.AgeCalculator;
import org.example.citronix.utility.ProductivityCalculator;
import org.example.citronix.utility.ValidationUtils;
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
        // Récupérer le champ associé
        Champ champ = champRepository.findById(arbreDTO.getChampId())
                .orElseThrow(() -> new IllegalArgumentException("Champ non trouvé."));

        // Valider les contraintes liées au champ et à la période de plantation
        ValidationUtils.validatePlantationPeriod(arbreDTO.getDatePlantation());
        validateTreeDensity(champ);

        // Mapper le DTO en entité
        Arbre arbre = ArbreMapper.INSTANCE.toEntity(arbreDTO);
        arbre.setChamp(champ);

        // Calculer l'âge et déterminer l'état de productivité
        int age = AgeCalculator.calculateAge(arbre.getDatePlantation());
        arbre.setAge(age);
        arbre.setEtatProductivite(ProductivityCalculator.determineProductivityState(age));

        // Sauvegarder et mapper en DTO
        Arbre savedArbre = arbreRepository.save(arbre);
        return ArbreMapper.INSTANCE.toDTO(savedArbre);
    }

    @Override
    public ArbreDTO modifierArbre(Long id, ArbreDTO arbreDTO) {
        // Récupérer l'arbre existant
        Arbre existingArbre = arbreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Arbre non trouvé."));

        // Récupérer et valider le champ
        Champ champ = champRepository.findById(arbreDTO.getChampId())
                .orElseThrow(() -> new IllegalArgumentException("Champ non trouvé."));
        ValidationUtils.validatePlantationPeriod(arbreDTO.getDatePlantation());
        validateTreeDensity(champ);

        // Mettre à jour les champs
        existingArbre.setType(arbreDTO.getType());
        existingArbre.setDatePlantation(arbreDTO.getDatePlantation());
        existingArbre.setChamp(champ);

        // Recalculer l'âge et l'état de productivité
        int age = AgeCalculator.calculateAge(arbreDTO.getDatePlantation());
        existingArbre.setAge(age);
        existingArbre.setEtatProductivite(ProductivityCalculator.determineProductivityState(age));

        // Sauvegarder et retourner le DTO
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

    // Méthode utilitaire pour valider la densité des arbres dans un champ
    private void validateTreeDensity(Champ champ) {
        int maxDensity = (int) (champ.getSuperficie() * 100); // 100 arbres par hectare
        int currentTreeCount = champ.getArbres().size();

        if (currentTreeCount >= maxDensity) {
            throw new IllegalArgumentException("La densité maximale d'arbres pour ce champ est atteinte.");
        }
    }
}
