package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Id;
import roomescape.domain.Name;
import roomescape.domain.Reservation;

public class ReservationRequest {
    private String name;
    private LocalDate date;
    private LocalTime time;

    public ReservationRequest() {
    }

    public ReservationRequest(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation toReservation(Long id) {
        return new Reservation(new Id(id), new Name(name), date, time);
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
