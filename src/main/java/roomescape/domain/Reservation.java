package roomescape.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record Reservation(Long id, String name, LocalDate date, ReservationTime time) {

    public Reservation(String name, LocalDate date, ReservationTime time) {
        this(null, name, date, time);
    }

    public static Reservation of(Long id, Reservation reservation) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    public String date(DateTimeFormatter formatter) {
        return date.format(formatter);
    }
}
