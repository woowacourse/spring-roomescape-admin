package roomescape.reservation.dto;

import java.time.LocalDate;
import roomescape.reservation.entity.Reservation;

public record ReservationResponseDto(Long id, String name, LocalDate date,
                                     ReservationTimeResponseDto time) {

    public static ReservationResponseDto toDto(Reservation reservation) {
        ReservationTimeResponseDto reservationTimeResponseDto = ReservationTimeResponseDto.toDto(
                reservation.getReservationTime());

        return new ReservationResponseDto(reservation.getId(),
                reservation.getName(),
                reservation.getReservationDate(),
                reservationTimeResponseDto);
    }
}
