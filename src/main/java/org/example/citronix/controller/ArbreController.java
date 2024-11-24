package org.example.citronix.controller;

import org.example.citronix.dto.ArbreDTO;
import org.example.citronix.service.arbre.ArbreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/arbres")
public class ArbreController {
    private final ArbreService arbreService;

    public ArbreController(ArbreService arbreService) {
        this.arbreService = arbreService;
    }

    @PostMapping
    public ResponseEntity<ArbreDTO> ajouterArbre(@Valid @RequestBody ArbreDTO arbreDTO) {
        return ResponseEntity.ok(arbreService.ajouterArbre(arbreDTO));
    }

    @GetMapping("/champ/{champId}")
    public ResponseEntity<List<ArbreDTO>> getArbresByChamp(@PathVariable Long champId) {
        return ResponseEntity.ok(arbreService.getArbresByChamp(champId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArbreDTO> getArbreById(@PathVariable Long id) {
        return ResponseEntity.ok(arbreService.getArbreById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArbreDTO> modifierArbre(@PathVariable Long id, @Valid @RequestBody ArbreDTO arbreDTO) {
        return ResponseEntity.ok(arbreService.modifierArbre(id, arbreDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerArbre(@PathVariable Long id) {
        arbreService.supprimerArbre(id);
        return ResponseEntity.noContent().build();
    }
}
