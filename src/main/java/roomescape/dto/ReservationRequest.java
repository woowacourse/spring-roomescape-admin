package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {

    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public ReservationRequest(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation toReservation(Long id) {
        return new Reservation(id, this.name, this.date, this.time);
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
