package org.example.citronix.service;

import org.example.citronix.dto.FermeDTO;
import org.example.citronix.entity.Ferme;
import org.example.citronix.repository.FermeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FermeServiceTest {

    @Mock
    private FermeRepository fermeRepository;

    @InjectMocks
    private FermeService fermeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreerFerme_Success() {
        // Given
        FermeDTO fermeDTO = new FermeDTO();
        fermeDTO.setNom("Ferme Test");
        fermeDTO.setLocalisation("Casablanca");
        fermeDTO.setSuperficie(50.0);

        Ferme ferme = new Ferme();
        ferme.setNom("Ferme Test");
        ferme.setLocalisation("Casablanca");
        ferme.setSuperficie(50.0);

        when(fermeRepository.save(any(Ferme.class))).thenReturn(ferme);

        // When
        FermeDTO result = fermeService.creerFerme(fermeDTO);

        // Then
        assertEquals("Ferme Test", result.getNom());
        assertEquals("Casablanca", result.getLocalisation());
        assertEquals(50.0, result.getSuperficie());
        verify(fermeRepository, times(1)).save(any(Ferme.class));
    }

    @Test
    void testCreerFerme_InvalidSuperficie() {
        // Given
        FermeDTO fermeDTO = new FermeDTO();
        fermeDTO.setNom("Ferme Invalid");
        fermeDTO.setLocalisation("Marrakech");
        fermeDTO.setSuperficie(150.0); // Invalid value

        // When & Then
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> fermeService.creerFerme(fermeDTO)
        );

        assertEquals("La superficie d'une ferme ne peut pas dépasser 100 hectares.", exception.getMessage());
    }
}
