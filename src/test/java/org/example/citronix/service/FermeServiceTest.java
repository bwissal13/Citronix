package org.example.citronix.service;

import org.example.citronix.dto.FermeDTO;
import org.example.citronix.entity.Ferme;
import org.example.citronix.repository.FermeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> fermeService.creerFerme(fermeDTO)
        );

        assertEquals("La superficie d'une ferme ne peut pas dépasser 100 hectares.", exception.getMessage());
    }
    @Test
    void testModifierFerme_Success() {
        // Given
        Long fermeId = 1L;
        Ferme existingFerme = new Ferme();
        existingFerme.setId(fermeId);
        existingFerme.setNom("Ferme Originale");
        existingFerme.setLocalisation("Casablanca");
        existingFerme.setSuperficie(50.0);
        existingFerme.setDateCreation(LocalDate.of(2010, 1, 1));

        FermeDTO fermeDTO = new FermeDTO();
        fermeDTO.setNom("Ferme Modifiée");
        fermeDTO.setLocalisation("Rabat");
        fermeDTO.setSuperficie(30.0);
        fermeDTO.setDateCreation(LocalDate.of(2015, 5, 20));

        when(fermeRepository.findById(fermeId)).thenReturn(Optional.of(existingFerme));
        when(fermeRepository.save(any(Ferme.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        FermeDTO result = fermeService.modifierFerme(fermeId, fermeDTO);

        // Then
        assertEquals("Ferme Modifiée", result.getNom());
        assertEquals("Rabat", result.getLocalisation());
        assertEquals(30.0, result.getSuperficie());
        verify(fermeRepository, times(1)).save(existingFerme);
    }
    @Test
    void testConsulterFerme_Success() {
        // Given
        Long fermeId = 1L;
        Ferme ferme = new Ferme();
        ferme.setId(fermeId);
        ferme.setNom("Ferme de Citrons");
        ferme.setLocalisation("Marrakech");
        ferme.setSuperficie(50.0);

        when(fermeRepository.findById(fermeId)).thenReturn(Optional.of(ferme));

        // When
        FermeDTO result = fermeService.consulterFerme(fermeId);

        // Then
        assertEquals("Ferme de Citrons", result.getNom());
        assertEquals("Marrakech", result.getLocalisation());
        assertEquals(50.0, result.getSuperficie());
        verify(fermeRepository, times(1)).findById(fermeId);
    }
    @Test
    void testConsulterFerme_NotFound() {
        // Given
        Long fermeId = 1L;
        when(fermeRepository.findById(fermeId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> fermeService.consulterFerme(fermeId)
        );

        assertEquals("Ferme non trouvée.", exception.getMessage());
        verify(fermeRepository, times(1)).findById(fermeId);
    }
}
