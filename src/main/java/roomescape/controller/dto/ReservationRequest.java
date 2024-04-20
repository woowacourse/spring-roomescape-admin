package roomescape.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.entity.Reservation;

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

    public Reservation toEntity() {
        return new Reservation(name, date, time);
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
