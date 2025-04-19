package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final String name;
    private final ReservationDateTime reservationDateTime;

    public Reservation(final String name, final ReservationDateTime reservationDateTime) {
        this.name = name;
        this.reservationDateTime = reservationDateTime;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReservationDate() {
        return reservationDateTime.getDate();
    }

    public LocalTime getReservationTime() {
        return reservationDateTime.getTime();
    }
}
