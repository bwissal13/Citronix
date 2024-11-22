package org.example.citronix.utility;

import java.time.LocalDate;
import java.time.Month;

public class ValidationUtils {

    public static void validatePlantationPeriod(LocalDate datePlantation) {
        if (datePlantation == null) {
            throw new IllegalArgumentException("La date de plantation ne peut pas être nulle.");
        }

        Month month = datePlantation.getMonth();
        if (month.getValue() < Month.MARCH.getValue() || month.getValue() > Month.MAY.getValue()) {
            throw new IllegalArgumentException("Les arbres ne peuvent être plantés qu'entre mars et mai.");
        }
    }
}
