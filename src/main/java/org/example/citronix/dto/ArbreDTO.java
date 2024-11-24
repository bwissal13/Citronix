package org.example.citronix.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.citronix.enums.EtatProductivite;

import java.time.LocalDate;

@Data

public class ArbreDTO {

    private String type = "Citronnier";
    @NotNull(message = "La date de plantation est obligatoire.")
    @PastOrPresent(message = "La date de plantation ne peut pas être dans le futur.")
    private LocalDate datePlantation;

    @NotNull(message = "L'ID du champ est obligatoire.")
    private Long champId;
    private EtatProductivite etatProductivite;
    private int age;
}
