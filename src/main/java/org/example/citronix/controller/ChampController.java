package org.example.citronix.controller;

import org.example.citronix.dto.ChampDTO;
import org.example.citronix.service.champ.ChampService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/champs")
public class ChampController {
    private final ChampService champService;

    public ChampController(ChampService champService) {
        this.champService = champService;
    }

    @PostMapping
    public ResponseEntity<ChampDTO> createChamp(@Valid @RequestBody ChampDTO champDTO) {
        return ResponseEntity.ok(champService.createChamp(champDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChampDTO> updateChamp(@PathVariable Long id, @Valid @RequestBody ChampDTO champDTO) {
        return ResponseEntity.ok(champService.updateChamp(id, champDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChamp(@PathVariable Long id) {
        champService.deleteChamp(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChampDTO> getChampById(@PathVariable Long id) {
        return ResponseEntity.ok(champService.getChampById(id));
    }

    @GetMapping("/ferme/{fermeId}")
    public ResponseEntity<List<ChampDTO>> getChampsByFerme(@PathVariable Long fermeId) {
        return ResponseEntity.ok(champService.getChampsByFerme(fermeId));
    }
}
