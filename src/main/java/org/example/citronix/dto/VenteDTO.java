package org.example.citronix.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class VenteDTO {

    @NotBlank(message = "Le nom du client est obligatoire.")
    private String client;

    @NotNull(message = "L'identifiant de la récolte est obligatoire.")
    @Positive(message = "L'identifiant de la récolte doit être positif.")
    private Long recolteId;

    @NotNull(message = "La date de vente est obligatoire.")
    @PastOrPresent(message = "La date de vente ne peut pas être dans le futur.")
    private LocalDate dateVente;

    @DecimalMin(value = "0.01", message = "Le prix unitaire doit être supérieur à 0.")
    private double prixUnitaire;

    @DecimalMin(value = "0.01", message = "La quantité doit être supérieure à 0.")
    private double quantite;
    private double total;
}
