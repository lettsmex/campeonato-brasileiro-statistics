package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator {
    public static boolean year2008(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return localDate.getYear() == 2008;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean Date2003to2022(String date, int startYear, int endYear) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int year = localDate.getYear();
            return year >= startYear && year <= endYear;
        } catch (DateTimeParseException e) {
            return false;
        }

    }

}
