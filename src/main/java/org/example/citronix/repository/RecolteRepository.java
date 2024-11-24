package org.example.citronix.repository;

import org.example.citronix.entity.Recolte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecolteRepository extends JpaRepository<Recolte, Long> {
    boolean existsByChampIdAndSaison(Long champId, Enum saison);
}
