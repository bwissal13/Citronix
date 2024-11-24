package org.example.citronix.service.detailRecolte;

import org.example.citronix.dto.DetailRecolteDTO;
import org.example.citronix.dto.RecolteDTO;
import org.example.citronix.entity.DetailRecolte;
import org.example.citronix.entity.Arbre;
import org.example.citronix.entity.Recolte;
import org.example.citronix.mapper.DetailRecolteMapper;
import org.example.citronix.repository.DetailRecolteRepository;
import org.example.citronix.repository.ArbreRepository;
import org.example.citronix.repository.RecolteRepository;

import org.example.citronix.utility.AgeCalculator;
import org.example.citronix.utility.ProductivityCalculator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DetailRecolteServiceImpl implements DetailRecolteService {

    private final DetailRecolteRepository detailRecolteRepository;
    private final ArbreRepository arbreRepository;
    private final RecolteRepository recolteRepository;

    public DetailRecolteServiceImpl(DetailRecolteRepository detailRecolteRepository, ArbreRepository arbreRepository, RecolteRepository recolteRepository) {
        this.detailRecolteRepository = detailRecolteRepository;
        this.arbreRepository = arbreRepository;
        this.recolteRepository = recolteRepository;
    }

    public List<DetailRecolteDTO> ajouterDetailsRecolte(DetailRecolteDTO detailRecolteDTO) {
        Recolte recolte = recolteRepository.findById(detailRecolteDTO.getRecolteId())
                .orElseThrow(() -> new IllegalArgumentException("Récolte non trouvée"));

        double quantiteTotale = 0;
        Arbre arbre = arbreRepository.findById(detailRecolteDTO.getArbreId())
                .orElseThrow(() -> new IllegalArgumentException("Arbre non trouvé"));
        double productiviteParArbre = calculerQuantiteRecoltee(arbre);
        double quantitePourArbre = detailRecolteDTO.getQuantite() * productiviteParArbre;
        quantiteTotale += quantitePourArbre;
        DetailRecolte detailRecolte = DetailRecolte.builder()
                .arbre(arbre)
                .recolte(recolte)
                .quantite(quantitePourArbre)
                .build();
        detailRecolteRepository.save(detailRecolte);
        recolte.setQuantiteTotale(recolte.getQuantiteTotale() + quantiteTotale);
        recolteRepository.save(recolte);
        DetailRecolteDTO resultDTO = new DetailRecolteDTO();
        resultDTO.setArbreId(detailRecolteDTO.getArbreId());
        resultDTO.setRecolteId(detailRecolteDTO.getRecolteId());
        resultDTO.setQuantite(quantitePourArbre);
        return List.of(resultDTO);
    }
    public double calculerQuantiteRecoltee(Arbre arbre) {
        if (arbre == null || arbre.getDatePlantation() == null) {
            throw new IllegalArgumentException("L'arbre ou sa date de plantation ne peut pas être nulle.");
        }
        int age = AgeCalculator.calculateAge(arbre.getDatePlantation());
        double productivite;
        if (age < 3) {
            productivite = 2.5;
        } else if (age <= 10) {
            productivite = 12;
        } else if (age <= 20) {
            productivite = 20;
        } else {
            productivite = 0;
        }

        return productivite;
    }
}
