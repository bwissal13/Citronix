package org.example.citronix.repository;

import org.example.citronix.entity.Champ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChampRepository extends JpaRepository<Champ, Long> {
    List<Champ> findByFermeId(Long fermeId);
}
