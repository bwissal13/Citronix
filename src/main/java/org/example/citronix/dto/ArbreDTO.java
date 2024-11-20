package org.example.citronix.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data

public class ArbreDTO {
    @NotBlank(message = "Le type de l'arbre est obligatoire.")
    @Size(max = 100, message = "Le type de l'arbre ne doit pas dépasser 100 caractères.")
    private String type;

    @NotNull(message = "La date de plantation est obligatoire.")
    @PastOrPresent(message = "La date de plantation ne peut pas être dans le futur.")
    private LocalDate datePlantation;

    @NotNull(message = "L'ID du champ est obligatoire.")
    private Long champId;

    private int age;
}
