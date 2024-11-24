package org.example.citronix.repository;

import org.example.citronix.entity.Arbre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArbreRepository extends JpaRepository<Arbre, Long> {
    List<Arbre> findByChampId(Long champId);
}
