package org.example.citronix.utility;

import org.example.citronix.enums.EtatProductivite;

public class ProductivityCalculator {


    public static EtatProductivite determineProductivityState(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("L'âge ne peut pas être négatif.");
        }
        if (age < 3) {
            return EtatProductivite.JEUNE;
        } else if (age <= 10) {
            return EtatProductivite.MATURE;
        } else if (age <= 20) {
            return EtatProductivite.VIEUX;
        } else {
            return EtatProductivite.NON_PRODUCTIF;
        }
    }
}
