package roomescape.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import roomescape.reservationtime.ReservationTime;

import java.time.LocalDate;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime reservationTime;

    public Reservation(final Long id, final String name, final LocalDate date, final ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
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

    @JsonProperty(value = "time")
    public ReservationTime getReservationTime() {
        return reservationTime;
    }
}
