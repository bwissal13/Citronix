package org.example.citronix.service.recolte;

import org.example.citronix.dto.RecolteDTO;
import org.example.citronix.dto.DetailRecolteDTO;
import org.example.citronix.entity.Champ;
import org.example.citronix.entity.Recolte;
import org.example.citronix.entity.DetailRecolte;
import org.example.citronix.entity.Arbre;
import org.example.citronix.mapper.DetailRecolteMapper;
import org.example.citronix.mapper.RecolteMapper;
import org.example.citronix.repository.ChampRepository;
import org.example.citronix.repository.DetailRecolteRepository;
import org.example.citronix.repository.RecolteRepository;
import org.example.citronix.repository.ArbreRepository;
import org.example.citronix.utility.AgeCalculator;
import org.example.citronix.utility.ProductivityCalculator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecolteServiceImpl implements RecolteService {

    private final RecolteRepository recolteRepository;
    private final ArbreRepository arbreRepository;
    private final DetailRecolteRepository detailRecolteRepository;
    private final ChampRepository champRepository;

    public RecolteServiceImpl(RecolteRepository recolteRepository, ArbreRepository arbreRepository, DetailRecolteRepository detailRecolteRepository, ChampRepository champRepository) {
        this.recolteRepository = recolteRepository;
        this.arbreRepository = arbreRepository;
        this.detailRecolteRepository = detailRecolteRepository;
        this.champRepository = champRepository;
    }

    @Override
    public RecolteDTO ajouterRecolte(RecolteDTO recolteDTO) {
        boolean existeDeja = recolteRepository.existsByChampIdAndSaison(
                recolteDTO.getChampID(),
                recolteDTO.getSaison()
        );
        if (existeDeja) {
            throw new IllegalArgumentException("Une récolte existe déjà pour ce champ dans cette saison.");
        }
        Recolte recolte = RecolteMapper.INSTANCE.toEntity(recolteDTO);
        Champ champ = champRepository.findById(recolteDTO.getChampID()).orElseThrow(() -> new RuntimeException());
        recolte.setChamp(champ);
        double quantiteTotale = 0;
        recolte.setQuantiteTotale(quantiteTotale);
        recolte.setDateRecolte(recolteDTO.getDateRecolte());
        Recolte savedRecolte = recolteRepository.save(recolte);
        return RecolteMapper.INSTANCE.toDTO(savedRecolte);
    }
    @Override
    public List<RecolteDTO> getAllRecoltes() {
        return recolteRepository.findAll().stream()
                .map(RecolteMapper.INSTANCE::toDTO)
                .toList();
    }
    @Override
    public RecolteDTO getRecolteById(Long recolteId) {
        System.out.println("Fetching recolte with ID: " + recolteId);
        Recolte recolte = recolteRepository.findById(recolteId)
                .orElseThrow(() -> new IllegalArgumentException("Récolte avec l'ID " + recolteId + " non trouvée."));
        System.out.println("Found recolte: " + recolte);
        RecolteDTO recolteDTO = RecolteMapper.INSTANCE.toDTO(recolte);
        System.out.println("Converted recolte to DTO: " + recolteDTO);
        return recolteDTO;
    }

    @Override
    public void supprimerRecolte(Long id) {
        Recolte recolte = recolteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Récolte avec l'ID " + id + " non trouvée"));
        recolteRepository.delete(recolte);
    }
    @Override
    public RecolteDTO updateRecolte(Long recolteId, RecolteDTO recolteDTO) {
        Recolte recolte = recolteRepository.findById(recolteId)
                .orElseThrow(() -> new IllegalArgumentException("Récolte avec l'ID " + recolteId + " non trouvée"));
        recolte.setSaison(recolteDTO.getSaison());
        recolte.setQuantiteTotale(recolteDTO.getQuantiteTotale());
        recolte.setDateRecolte(recolteDTO.getDateRecolte());
        Recolte updatedRecolte = recolteRepository.save(recolte);

        return RecolteMapper.INSTANCE.toDTO(updatedRecolte);
    }
}
