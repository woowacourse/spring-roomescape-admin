package roomescape.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record Reservation(Long id, String name, LocalDate date, ReservationTime time) {

    public Reservation assignId(Long id) {
        return new Reservation(id, name, date, time);
    }

    public Reservation assignTime(ReservationTime time) {
        return new Reservation(id, name, date, time);
    }

    public String formatDate() {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
