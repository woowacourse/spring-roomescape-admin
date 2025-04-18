package roomescape.reservation.model;

import java.time.LocalDate;
import java.time.LocalTime;

public final class Reservation {

    private final ReservationId id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(ReservationId id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id.getId();
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
