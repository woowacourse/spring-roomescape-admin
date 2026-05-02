package roomescape.reservation.controller.dto;

import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.ReservationTime;

import java.time.LocalDate;

public record ReservationResponseDto(Long id, String name, LocalDate date, ReservationTime reservationTime) {

    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
