package org.example.citronix.dto;

import lombok.*;

@Data
public class DetailRecolteDTO {
    private Long id;
    private double quantite=1;
    private Long arbreId;
    private Long recolteId;
}
