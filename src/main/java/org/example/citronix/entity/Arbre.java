package org.example.citronix.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.example.citronix.enums.EtatProductivite;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Arbre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type = "Citronnier";

    @NotNull(message = "La date de plantation est obligatoire.")
    @PastOrPresent(message = "La date de plantation ne peut pas être dans le futur.")
    private LocalDate datePlantation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "champ_id", nullable = false)
    private Champ champ;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatProductivite etatProductivite;


    private int age;
}
