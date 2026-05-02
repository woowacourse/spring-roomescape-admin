package roomescape.reservation.controller.dto;

import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.ReservationTime;

import java.time.LocalDate;

public class ReservationResponseDto {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime reservationTime;

    public ReservationResponseDto(Long id, String name, LocalDate date, ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }

    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
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

    public ReservationTime getReservationTime() {
        return reservationTime;
    }
}
