package org.example.citronix.repository;

import org.example.citronix.entity.DetailRecolte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailRecolteRepository extends JpaRepository<DetailRecolte, Long> {
    boolean existsByArbreIdAndRecolteSaison(long arbreId, Enum saison);
}
