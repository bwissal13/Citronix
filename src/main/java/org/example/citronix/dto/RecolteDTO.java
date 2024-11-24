package org.example.citronix.dto;

import lombok.*;
import org.example.citronix.entity.Champ;
import org.example.citronix.enums.Saison;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RecolteDTO {
    private Long id;
    private Saison saison;
    private Long champID;
    private double quantiteTotale;
    private LocalDateTime dateRecolte;
    private List<DetailRecolteDTO> detailsRecolte= new ArrayList<>();
}
