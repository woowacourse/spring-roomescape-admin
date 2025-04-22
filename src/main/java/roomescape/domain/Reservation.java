package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final ReservationName name;
    private final ReservationDate date;
    private final ReservationTime time;

    public Reservation(
            final Long id,
            final ReservationName name,
            final ReservationDate date,
            final ReservationTime time
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name.getValue();
    }

    public LocalDate date() {
        return date.date();
    }

    public LocalTime time() {
        return time.time();
    }

    public ReservationDate reservationDate() {
        return date;
    }

    public ReservationTime reservationTime() {
        return time;
    }
}
