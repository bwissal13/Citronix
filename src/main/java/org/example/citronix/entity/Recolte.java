package org.example.citronix.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.example.citronix.enums.Saison;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Recolte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Saison saison;

    @PastOrPresent(message = "La date de récolte doit être dans le passé ou aujourd'hui.")
    private LocalDate dateRecolte;

    @ManyToOne
    @JoinColumn(name = "champ_id", nullable = false)
    private Champ champ;

    @ManyToMany
    @JoinTable(
            name = "recolte_arbre",
            joinColumns = @JoinColumn(name = "recolte_id"),
            inverseJoinColumns = @JoinColumn(name = "arbre_id")
    )
    private List<Arbre> arbres;

    public double getQuantite() {
        return calculateQuantite();
    }

    private double calculateQuantite() {
        return arbres.stream()
                .mapToDouble(this::calculateProductivity)
                .sum();
    }

    private double calculateProductivity(Arbre arbre) {
        int age = arbre.getAge();
        if (age < 3) {
            return 2.5;
        } else if (age <= 10) {
            return 12;
        } else if (age <= 20) {
            return 20;
        }
        return 0;
    }
}
