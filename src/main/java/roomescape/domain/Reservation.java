package roomescape.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record Reservation(Long id, String name, LocalDate date, ReservationTime time) {

    public static Reservation of(Long id, Reservation reservation, ReservationTime time) {
        return new Reservation(id, reservation.name, reservation.date, time);
    }

    public static Reservation of(Long id, String name, LocalDate date, Long timeId) {
        return new Reservation(id, name, date, ReservationTime.from(timeId));
    }

    public String date(DateTimeFormatter formatter) {
        return date.format(formatter);
    }
}
