package org.example.citronix.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FermeDTO {
    @NotBlank(message = "Le nom de la ferme est obligatoire.")
    private String nom;

    @NotBlank(message = "La localisation est obligatoire.")
    private String localisation;

    @Positive(message = "La superficie doit être supérieure à zéro.")
    private double superficie;

    @NotNull(message = "La date de création est obligatoire.")
    private LocalDate dateCreation;
}
