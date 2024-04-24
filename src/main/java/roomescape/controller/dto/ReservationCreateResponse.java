package roomescape.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.entity.Reservation;

public class ReservationCreateResponse {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public ReservationCreateResponse(long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationCreateResponse from(Reservation reservation) {
        return new ReservationCreateResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getStartDate(),
                reservation.getStartTime());
    }

    public long getId() {
        return id;
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
