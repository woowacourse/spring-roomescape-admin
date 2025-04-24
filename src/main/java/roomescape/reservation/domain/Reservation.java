package roomescape.reservation.domain;

import java.time.LocalDate;
import roomescape.reservationtime.domain.ReservationTime;

public class Reservation {
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(final String name, final LocalDate date, final ReservationTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
