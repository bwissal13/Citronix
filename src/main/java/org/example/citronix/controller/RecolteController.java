package org.example.citronix.controller;

import lombok.AllArgsConstructor;
import org.example.citronix.dto.RecolteDTO;
import org.example.citronix.dto.DetailRecolteDTO;
import org.example.citronix.service.detailRecolte.DetailRecolteService;
import org.example.citronix.service.detailRecolte.DetailRecolteServiceImpl;
import org.example.citronix.service.recolte.RecolteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recoltes")
@AllArgsConstructor
public class RecolteController {

    private final RecolteService recolteService;
    private final DetailRecolteService detailRecolteService;

    @PostMapping
    public ResponseEntity<RecolteDTO> ajouterRecolte(@RequestBody RecolteDTO recolteDTO) {
        RecolteDTO savedRecolte = recolteService.ajouterRecolte(recolteDTO);
        return new ResponseEntity<>(savedRecolte, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RecolteDTO>> getAllRecoltes() {
        List<RecolteDTO> recoltes = recolteService.getAllRecoltes();
        return new ResponseEntity<>(recoltes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecolteDTO> getRecolteById(@PathVariable Long id) {
        RecolteDTO recolteDTO = recolteService.getRecolteById(id);
        return new ResponseEntity<>(recolteDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecolteDTO> updateRecolte(@PathVariable Long id, @RequestBody RecolteDTO recolteDTO) {
        recolteDTO.setId(id);
        RecolteDTO updatedRecolte = recolteService.updateRecolte(id,recolteDTO);
        return new ResponseEntity<>(updatedRecolte, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecolte(@PathVariable Long id) {
        recolteService.supprimerRecolte(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/details")
    public ResponseEntity<List<DetailRecolteDTO>> ajouterDetailsRecolte(@PathVariable Long id, @RequestBody DetailRecolteDTO recolteDTO) {
       List<DetailRecolteDTO> details = detailRecolteService.ajouterDetailsRecolte(recolteDTO);
        return new ResponseEntity<>(details, HttpStatus.CREATED);
    }
}
