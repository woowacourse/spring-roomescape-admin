package roomescape.reservation.model;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservationtime.model.ReservationTime;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime reservationTime;

    public Reservation(final Long id, final String name, final LocalDate date, final Long timeId, final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = new ReservationTime(timeId, time);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }
}
