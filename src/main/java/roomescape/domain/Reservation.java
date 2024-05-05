package roomescape.domain;

import java.time.LocalDate;

public record Reservation(Long id, String name, LocalDate date, ReservationTime time) {

    public Reservation(String name, LocalDate date, ReservationTime time) {
        this(null, name, date, time);
    }
}
