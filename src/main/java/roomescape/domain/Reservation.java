package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final ReservationName name;
    private final ReservationDateTime dateTime;

    public Reservation(final Long id, final ReservationName name, final ReservationDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name.getValue();
    }

    public LocalDate date() {
        return dateTime.date();
    }

    public LocalTime time() {
        return dateTime.time();
    }

    public ReservationDateTime reservationDateTime() {
        return dateTime;
    }

    public ReservationTime reservationTime() {
        return dateTime.reservationTime();
    }
}
