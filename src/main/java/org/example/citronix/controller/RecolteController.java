package org.example.citronix.controller;

import org.example.citronix.dto.RecolteDTO;
import org.example.citronix.service.recolte.RecolteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recoltes")
public class RecolteController {

    private final RecolteService recolteService;

    public RecolteController(RecolteService recolteService) {
        this.recolteService = recolteService;
    }

    @PostMapping
    public ResponseEntity<RecolteDTO> ajouterRecolte(@RequestBody RecolteDTO recolteDTO) {
        RecolteDTO createdRecolte = recolteService.ajouterRecolte(recolteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecolte);
    }


    @GetMapping("/champ/{champId}")
    public ResponseEntity<List<RecolteDTO>> getRecoltesByChamp(@PathVariable Long champId) {
        return ResponseEntity.ok(recolteService.getRecoltesByChamp(champId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<RecolteDTO> getRecolteById(@PathVariable Long id) {
        return ResponseEntity.ok(recolteService.getRecolteById(id));
    }
}
