package org.example.citronix.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ChampDTO {
    @NotBlank(message = "Le nom du champ est obligatoire.")
    @Size(max = 100, message = "Le nom du champ ne doit pas dépasser 100 caractères.")
    private String nom;
    @Positive(message = "La superficie doit être un nombre positif.")
    @DecimalMin(value = "0.1", inclusive = true, message = "La superficie doit être d'au moins 0.1 hectare (1 000 m²).")
    @DecimalMax(value = "50.0", inclusive = true, message = "La superficie ne doit pas dépasser 50 hectares.")
    private double superficie;
    @NotNull(message = "L'ID de la ferme est obligatoire.")
    private Long fermeId;
}
