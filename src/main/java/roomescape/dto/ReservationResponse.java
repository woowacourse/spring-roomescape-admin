package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;

public class ReservationResponse {
    private Long id;
    private String name;
    private LocalDate date;
    private TimeResponse time;

    public ReservationResponse(Long id, String name, LocalDate date, TimeResponse time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                TimeResponse.from(reservation.getReservationTime())
        );
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

    public TimeResponse getTime() {
        return time;
    }
}
