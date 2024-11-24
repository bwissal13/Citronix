package org.example.citronix.service.vente;

import lombok.AllArgsConstructor;
import org.example.citronix.dto.VenteDTO;
import org.example.citronix.entity.Recolte;
import org.example.citronix.entity.Vente;
import org.example.citronix.mapper.VenteMapper;
import org.example.citronix.repository.RecolteRepository;
import org.example.citronix.repository.VenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VenteServiceImpl implements VenteService {

    private VenteRepository venteRepository;
    private RecolteRepository recolteRepository;
    private VenteMapper venteMapper;

    @Override

    public VenteDTO enregistrerVente(VenteDTO venteDTO) {
        Recolte recolte = recolteRepository.findById(venteDTO.getRecolteId())
                .orElseThrow(() -> new IllegalArgumentException("Récolte non trouvée"));

        if (venteDTO.getQuantite() > recolte.getQuantiteTotale()) {
            throw new IllegalArgumentException("Quantité insuffisante dans la récolte");
        }

        recolte.setQuantiteTotale(recolte.getQuantiteTotale() - venteDTO.getQuantite());
        recolteRepository.save(recolte);

        double total = venteDTO.getPrixUnitaire() * venteDTO.getQuantite();

        Vente vente = venteMapper.toEntity(venteDTO);
        vente.setRecolte(recolte);
        vente.setTotal(total);
        Vente savedVente = venteRepository.save(vente);

        VenteDTO savedVenteDTO = venteMapper.toDTO(savedVente);
        savedVenteDTO.setTotal(total);
        return savedVenteDTO;
    }

    @Override
    public List<VenteDTO> getAllVentes() {
        List<Vente> ventes = venteRepository.findAll();
        return ventes.stream().map(venteMapper::toDTO).toList();
    }

    @Override
    public List<VenteDTO> getVentesByRecolteId(Long recolteId) {
        List<Vente> ventes = venteRepository.findByRecolteId(recolteId);
        return ventes.stream().map(venteMapper::toDTO).toList();
    }

    @Override
    public void supprimerVente(Long id) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vente non trouvée"));
        Recolte recolte = vente.getRecolte();
        recolte.setQuantiteTotale(recolte.getQuantiteTotale() + vente.getQuantite());
        recolteRepository.save(recolte);
        venteRepository.delete(vente);
    }

}
