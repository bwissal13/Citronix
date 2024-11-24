package org.example.citronix.service;

import org.example.citronix.dto.RecolteDTO;
import org.example.citronix.entity.Champ;
import org.example.citronix.entity.Recolte;
import org.example.citronix.enums.Saison;
import org.example.citronix.repository.ChampRepository;
import org.example.citronix.repository.RecolteRepository;
import org.example.citronix.service.recolte.RecolteServiceImpl;
import org.example.citronix.mapper.RecolteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RecolteServiceTest {

    @Mock
    private RecolteRepository recolteRepository;

    @Mock
    private ChampRepository champRepository;

    @InjectMocks
    private RecolteServiceImpl recolteService;

    private RecolteDTO recolteDTO;
    private Recolte recolte;
    private Champ champ;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialiser les objets pour les tests
        champ = new Champ();
        champ.setId(1L);

        recolte = new Recolte();
        recolte.setId(1L);
        recolte.setChamp(champ);
        recolte.setSaison(Saison.AUTOMNE);
        recolte.setQuantiteTotale(100);

        recolteDTO = new RecolteDTO();
        recolteDTO.setChampID(1L);
        recolteDTO.setSaison(Saison.AUTOMNE);
        recolteDTO.setQuantiteTotale(100);
        recolteDTO.setDateRecolte(LocalDateTime.parse("2024-05-01T10:00:00"));
    }

    @Test
    void testAjouterRecolte_Success() {
        // Arrange
        when(recolteRepository.existsByChampIdAndSaison(anyLong(), eq(Saison.ETE))).thenReturn(false);
        when(champRepository.findById(anyLong())).thenReturn(Optional.of(champ));
        when(recolteRepository.save(any(Recolte.class))).thenReturn(recolte);

        // Act
        RecolteDTO result = recolteService.ajouterRecolte(recolteDTO);

        // Assert
        assertNotNull(result);
        assertEquals(recolteDTO.getSaison(), result.getSaison());
        assertEquals(recolteDTO.getQuantiteTotale(), result.getQuantiteTotale());
        verify(recolteRepository, times(1)).save(any(Recolte.class));
    }


    @Test
    void testGetRecolteById_Success() {
        // Arrange
        when(recolteRepository.findById(anyLong())).thenReturn(Optional.of(recolte));

        // Act
        RecolteDTO result = recolteService.getRecolteById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(recolte.getSaison(), result.getSaison());
        verify(recolteRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetRecolteById_NotFound() {
        // Arrange
        when(recolteRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            recolteService.getRecolteById(1L);
        });
    }

    @Test
    void testUpdateRecolte_Success() {
        // Arrange
        RecolteDTO updateDTO = new RecolteDTO();
        updateDTO.setSaison(Saison.AUTOMNE);
        updateDTO.setQuantiteTotale(200);
        when(recolteRepository.findById(anyLong())).thenReturn(Optional.of(recolte));
        when(recolteRepository.save(any(Recolte.class))).thenReturn(recolte);

        // Act
        RecolteDTO result = recolteService.updateRecolte(1L, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(Saison.AUTOMNE, result.getSaison());
        assertEquals(200, result.getQuantiteTotale());
    }

    @Test
    void testUpdateRecolte_NotFound() {
        // Arrange
        RecolteDTO updateDTO = new RecolteDTO();
        updateDTO.setSaison(Saison.AUTOMNE);
        updateDTO.setQuantiteTotale(200);
        when(recolteRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            recolteService.updateRecolte(1L, updateDTO);
        });
    }

    @Test
    void testSupprimerRecolte_Success() {
        // Arrange
        when(recolteRepository.findById(anyLong())).thenReturn(Optional.of(recolte));

        // Act
        recolteService.supprimerRecolte(1L);

        // Assert
        verify(recolteRepository, times(1)).delete(any(Recolte.class));
    }

    @Test
    void testSupprimerRecolte_NotFound() {
        // Arrange
        when(recolteRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            recolteService.supprimerRecolte(1L);
        });
    }
}
