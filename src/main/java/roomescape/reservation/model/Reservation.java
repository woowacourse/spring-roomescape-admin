package roomescape.reservation.model;

import java.time.LocalDate;

public final class Reservation {

    private final long id;
    private final ReservationDetails reservationDetails;

    public Reservation(long id, String name, LocalDate date, ReservationTime time) {
        this.id = id;
        this.reservationDetails = new ReservationDetails(name, date, time);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return reservationDetails.name();
    }

    public LocalDate getDate() {
        return reservationDetails.date();
    }

    public ReservationTime getTime() {
        return reservationDetails.time();
    }
}
