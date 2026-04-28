package roomescape;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Reservation {
    public Reservation(String name, String date, String time) {
        parseDate(date);
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException();
        }
    }
}
