package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Reservation {
    public Reservation(String name, String date, String time) {
        parseDate(date);
        parseTime(time);
    }

    private LocalTime parseTime(String time) {
        try {
            return LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException();
        }
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException();
        }
    }
}
