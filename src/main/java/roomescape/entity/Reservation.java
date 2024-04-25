package roomescape.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record Reservation(Long id, String name, LocalDate date, ReservationTime time) {

    public Reservation assign(Long id, ReservationTime time) {
        return new Reservation(id, name, date, time);
    }

    public String formatDate() {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String formatTime() {
        return time.formatTime();
    }
}
