package org.example.citronix.utility;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculator {
    public static int calculateAge(LocalDate datePlantation) {
        if (datePlantation == null) {
            throw new IllegalArgumentException("La date de plantation ne peut pas être nulle.");
        }
        return Period.between(datePlantation, LocalDate.now()).getYears();
    }
}

