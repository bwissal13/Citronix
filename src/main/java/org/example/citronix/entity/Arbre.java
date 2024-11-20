package org.example.citronix.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.citronix.entity.Champ;
import org.example.citronix.enums.EtatProductivite;

import java.time.LocalDate;

@Data
@Entity
public class Arbre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private LocalDate datePlantation;
    private int age;
    @ManyToOne
    @JoinColumn(name = "champ_id", nullable = false)
    private Champ champ;

    @Enumerated(EnumType.STRING)
    private EtatProductivite etatProductivite;
}
