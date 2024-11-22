package org.example.citronix.dto;

import lombok.Data;
import org.example.citronix.enums.Saison;

import java.time.LocalDate;
import java.util.List;
@Data
public class RecolteDTO {
    private Long id;
    private Saison saison;
    private LocalDate dateRecolte;
    private Long champId;
    private List<Long> arbresIds;


    private double quantite; 

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }
}
