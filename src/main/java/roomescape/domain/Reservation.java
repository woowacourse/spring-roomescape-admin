package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record Reservation(Long id, String name, LocalDate date, LocalTime time) {

    public static Reservation of(Long id, Reservation reservation) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    public String date(DateTimeFormatter formatter) {
        return date.format(formatter);
    }

    public String time(DateTimeFormatter formatter) {
        return time.format(formatter);
    }
}
