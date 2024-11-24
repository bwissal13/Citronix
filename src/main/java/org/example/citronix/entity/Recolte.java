package org.example.citronix.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.citronix.enums.Saison;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Recolte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "saison", nullable = false)
    private Saison saison;
    @ManyToOne
    @JoinColumn(name = "champ_id", nullable = false)
    private Champ champ;  


    @Column(nullable = false)
    private double quantiteTotale;

    @Column(nullable = false)
    private LocalDateTime dateRecolte;

    @OneToMany(mappedBy = "recolte", cascade = CascadeType.ALL)
    private List<DetailRecolte> detailsRecolte;

//    @OneToMany(mappedBy = "recolte")
//    private List<Vente> ventes;  // Ventes associées à cette récolte
}
