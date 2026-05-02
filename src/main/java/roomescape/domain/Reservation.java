package roomescape.domain;

import java.time.LocalDate;

public record Reservation(
    Long id,
    String name,
    LocalDate date,
    ReservationTime time
) {

    public Reservation(String name, LocalDate date, ReservationTime time) {
        this(null, name, date, time);
    }

    public Long getTimeId() {
        return time.id();
    }

    public Reservation withId(long id) {
        return new Reservation(id, name, date, time);
    }
}
