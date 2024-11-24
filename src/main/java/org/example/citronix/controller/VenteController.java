package org.example.citronix.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.citronix.dto.VenteDTO;
import org.example.citronix.service.vente.VenteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/ventes")
public class VenteController {


    private VenteService venteService;

    @PostMapping
    public ResponseEntity<VenteDTO> enregistrerVente( @Valid @RequestBody VenteDTO venteDTO) {
        VenteDTO savedVente = venteService.enregistrerVente(venteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVente);
    }

    @GetMapping
    public ResponseEntity<List<VenteDTO>> getAllVentes() {
        List<VenteDTO> ventes = venteService.getAllVentes();
        return ResponseEntity.ok(ventes);
    }


    @GetMapping("/recolte/{recolteId}")
    public ResponseEntity<List<VenteDTO>> getVentesByRecolteId(@PathVariable Long recolteId) {
        List<VenteDTO> ventes = venteService.getVentesByRecolteId(recolteId);
        return ResponseEntity.ok(ventes);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerVente(@PathVariable Long id) {
        venteService.supprimerVente(id);
        return ResponseEntity.noContent().build();
    }
}
