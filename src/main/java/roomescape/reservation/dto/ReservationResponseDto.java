package roomescape.reservation.dto;

import roomescape.reservation.entity.Reservation;

import java.time.LocalDate;

public record ReservationResponseDto(Long id, String name, LocalDate date, ReservationTimeResponseDto time) {

    public static ReservationResponseDto toDto(Reservation reservation) {
        return new ReservationResponseDto(
            reservation.getId(), reservation.getName(), reservation.getDate(),
            ReservationTimeResponseDto.toDto(reservation.getTime()));
    }
}
