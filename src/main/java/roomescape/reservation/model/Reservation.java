package roomescape.reservation.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDate;
import java.time.LocalTime;

public final class Reservation {

    private final long id;
    private final ReservationDetails reservationDetails;

    public Reservation(long id, ReservationDetails reservationDetails) {
        this.id = id;
        this.reservationDetails = reservationDetails;
    }

    @JsonCreator
    public Reservation(long id, String name, LocalDate date, LocalTime time) {
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

    public LocalTime getTime() {
        return reservationDetails.time();
    }
}
