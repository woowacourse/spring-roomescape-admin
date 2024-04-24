package roomescape.reservation.model;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservationtime.model.ReservationTime;

public class Reservation {
    private final Long id;
    private final ReservationName name;
    private final ReservationDate date;
    private final ReservationTime reservationTime;

    public Reservation(final Long id, final String name, final LocalDate date, final Long timeId, final LocalTime time) {
        this.id = id;
        this.name = new ReservationName(name);
        this.date = new ReservationDate(date);
        this.reservationTime = new ReservationTime(timeId, time);
    }

    public Long getId() {
        return id;
    }

    public ReservationName getName() {
        return name;
    }

    public ReservationDate getDate() {
        return date;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }
}
