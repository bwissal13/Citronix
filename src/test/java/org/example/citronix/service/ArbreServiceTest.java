package org.example.citronix.service;

import org.example.citronix.dto.ArbreDTO;
import org.example.citronix.entity.Arbre;
import org.example.citronix.entity.Champ;
import org.example.citronix.enums.EtatProductivite;
import org.example.citronix.repository.ArbreRepository;
import org.example.citronix.repository.ChampRepository;
import org.example.citronix.service.arbre.ArbreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArbreServiceTest {

    @Mock
    private ArbreRepository arbreRepository;

    @Mock
    private ChampRepository champRepository;

    @InjectMocks
    private ArbreServiceImpl arbreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAjouterArbre_Success() {
        ArbreDTO arbreDTO = new ArbreDTO();
        arbreDTO.setType("Citronnier");
        arbreDTO.setDatePlantation(LocalDate.of(2020, 3, 1));
        arbreDTO.setChampId(1L);

        Champ champ = new Champ();
        champ.setId(1L);
        champ.setSuperficie(1.0);
        champ.setArbres(List.of());

        Arbre arbre = new Arbre();
        arbre.setId(1L);
        arbre.setType("Citronnier");
        arbre.setDatePlantation(LocalDate.of(2020, 3, 1));
        arbre.setAge(3);
        arbre.setEtatProductivite(EtatProductivite.valueOf("MATURE"));
        arbre.setChamp(champ);

        when(champRepository.findById(1L)).thenReturn(Optional.of(champ));
        when(arbreRepository.save(any(Arbre.class))).thenReturn(arbre);

        // When
        ArbreDTO result = arbreService.ajouterArbre(arbreDTO);

        // Then
        assertNotNull(result);
        assertEquals("Citronnier", result.getType());
        verify(champRepository, times(1)).findById(1L);
        verify(arbreRepository, times(1)).save(any(Arbre.class));
    }


    @Test
    void testAjouterArbre_ChampNotFound() {
        // Given
        ArbreDTO arbreDTO = new ArbreDTO();
        arbreDTO.setChampId(999L);

        when(champRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> arbreService.ajouterArbre(arbreDTO));
        assertEquals("Champ non trouvé.", exception.getMessage());
        verify(champRepository, times(1)).findById(999L);
    }

    @Test
    void testModifierArbre_Success() {
        // Given
        Long arbreId = 1L;

        // Arbre existant à modifier
        Arbre existingArbre = new Arbre();
        existingArbre.setId(arbreId);
        existingArbre.setType("Original");
        existingArbre.setDatePlantation(LocalDate.of(2015, 3, 1));

        // Champ associé à l'arbre existant
        Champ originalChamp = new Champ();
        originalChamp.setId(1L);
        originalChamp.setSuperficie(1.0);
        originalChamp.setArbres(List.of(existingArbre));


        Champ updatedChamp = new Champ();
        updatedChamp.setId(2L);
        updatedChamp.setSuperficie(1.0);
        updatedChamp.setArbres(new ArrayList<>(Collections.nCopies(99, new Arbre())));

        ArbreDTO arbreDTO = new ArbreDTO();
        arbreDTO.setType("Modified");
        arbreDTO.setDatePlantation(LocalDate.of(2020, 4, 1));
        arbreDTO.setChampId(2L);

        when(arbreRepository.findById(arbreId)).thenReturn(Optional.of(existingArbre));
        when(champRepository.findById(1L)).thenReturn(Optional.of(originalChamp));
        when(champRepository.findById(2L)).thenReturn(Optional.of(updatedChamp));
        when(arbreRepository.save(any(Arbre.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        ArbreDTO result = arbreService.modifierArbre(arbreId, arbreDTO);

        // Then
        assertNotNull(result);
        assertEquals("Modified", result.getType());
        assertEquals(   4, result.getAge());
        verify(arbreRepository, times(1)).save(existingArbre);
        verify(champRepository, times(1)).findById(anyLong());
    }


    @Test
    void testModifierArbre_ArbreNotFound() {
        // Given
        Long arbreId = 1L;
        ArbreDTO arbreDTO = new ArbreDTO();

        when(arbreRepository.findById(arbreId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> arbreService.modifierArbre(arbreId, arbreDTO));
        assertEquals("Arbre non trouvé.", exception.getMessage());
        verify(arbreRepository, times(1)).findById(arbreId);
    }

    @Test
    void testGetArbresByChamp_Success() {
        // Given
        Long champId = 1L;
        Arbre arbre = new Arbre();
        arbre.setId(1L);
        arbre.setType("Citronnier");

        when(arbreRepository.findByChampId(champId)).thenReturn(List.of(arbre));

        // When
        List<ArbreDTO> result = arbreService.getArbresByChamp(champId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(arbreRepository, times(1)).findByChampId(champId);
    }

    @Test
    void testGetArbreById_Success() {
        // Given
        Long arbreId = 1L;
        Arbre arbre = new Arbre();
        arbre.setId(arbreId);
        arbre.setType("Citronnier");

        when(arbreRepository.findById(arbreId)).thenReturn(Optional.of(arbre));

        // When
        ArbreDTO result = arbreService.getArbreById(arbreId);

        // Then
        assertNotNull(result);
        assertEquals("Citronnier", result.getType());
        verify(arbreRepository, times(1)).findById(arbreId);
    }

    @Test
    void testSupprimerArbre_Success() {
        // Given
        Long arbreId = 1L;
        when(arbreRepository.existsById(arbreId)).thenReturn(true);

        // When
        arbreService.supprimerArbre(arbreId);

        // Then
        verify(arbreRepository, times(1)).deleteById(arbreId);
    }

    @Test
    void testSupprimerArbre_ArbreNotFound() {
        // Given
        Long arbreId = 999L;
        when(arbreRepository.existsById(arbreId)).thenReturn(false);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> arbreService.supprimerArbre(arbreId));
        assertEquals("Arbre non trouvé.", exception.getMessage());
        verify(arbreRepository, never()).deleteById(arbreId);
    }
    @Test
    void testAjouterArbre_DensiteMaximaleAtteinte() {
        // Given
        ArbreDTO arbreDTO = new ArbreDTO();
        arbreDTO.setType("Citronnier");
        arbreDTO.setDatePlantation(LocalDate.of(2020, 3, 1));
        arbreDTO.setChampId(1L);

        Champ champ = new Champ();
        champ.setId(1L);
        champ.setSuperficie(1.0); // Superficie de 1 hectare
        // Déjà 100 arbres dans le champ
        champ.setArbres(new ArrayList<>(Collections.nCopies(100, new Arbre())));

        when(champRepository.findById(1L)).thenReturn(Optional.of(champ));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> arbreService.ajouterArbre(arbreDTO));
        assertEquals("La densité maximale d'arbres pour ce champ est atteinte.", exception.getMessage());
        verify(champRepository, times(1)).findById(1L);
        verify(arbreRepository, never()).save(any(Arbre.class));
    }

}
