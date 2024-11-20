package org.example.citronix.controller;

import jakarta.validation.Valid;
import org.example.citronix.dto.FermeDTO;
import org.example.citronix.service.FermeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fermes")
public class FermeController {
    private final FermeService fermeService;

    public FermeController(FermeService fermeService) {
        this.fermeService = fermeService;
    }

    @PostMapping
    public ResponseEntity<FermeDTO> creerFerme(@Valid @RequestBody FermeDTO fermeDTO) {
        FermeDTO createdFerme = fermeService.creerFerme(fermeDTO);
        return ResponseEntity.ok(createdFerme);
    }
    @PutMapping("/{id}")
    public ResponseEntity<FermeDTO> modifierFerme(@PathVariable Long id, @Valid @RequestBody FermeDTO fermeDTO) {
        FermeDTO updatedFerme = fermeService.modifierFerme(id, fermeDTO);
        return ResponseEntity.ok(updatedFerme);
    }
    @GetMapping("/{id}")
    public ResponseEntity<FermeDTO> consulterFerme(@PathVariable Long id) {
        FermeDTO fermeDTO = fermeService.consulterFerme(id);
        return ResponseEntity.ok(fermeDTO);
    }
}
