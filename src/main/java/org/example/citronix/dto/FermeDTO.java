package org.example.citronix.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FermeDTO {
    @NotBlank(message = "Le nom de la ferme est obligatoire.")
    @Size(max = 100, message = "Le nom de la ferme ne doit pas dépasser 100 caractères.")
    private String nom;

    @NotBlank(message = "La localisation est obligatoire.")
    @Size(max = 150, message = "La localisation ne doit pas dépasser 150 caractères.")
    private String localisation;

    @Positive(message = "La superficie doit être un nombre positif.")
    @DecimalMin(value = "0.1", inclusive = true, message = "La superficie doit être d'au moins 0.1 hectare (1 000 m²).")
    private double superficie;

    @NotNull(message = "La date de création est obligatoire.")
    @PastOrPresent(message = "La date de création ne peut pas être dans le futur.")
    private LocalDate dateCreation;
}
