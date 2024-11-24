package org.example.citronix.repository;

import org.example.citronix.entity.Vente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenteRepository extends JpaRepository<Vente, Long> {
    List<Vente> findByRecolteId(Long recolteId);
}
