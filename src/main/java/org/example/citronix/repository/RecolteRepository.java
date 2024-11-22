package org.example.citronix.repository;

import org.example.citronix.entity.Champ;
import org.example.citronix.entity.Recolte;
import org.example.citronix.enums.Saison;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecolteRepository extends JpaRepository<Recolte, Long> {
    List<Recolte> findByChampId(Long champId);
    boolean existsByChampAndSaison(Champ champ, Saison saison);
}
