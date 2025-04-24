package roomescape.reservation.dto;

import java.time.LocalDate;
import roomescape.reservation.entity.Reservation;

public record ReservationResponseDto(Long id, String name, LocalDate date,
                                     ReservationTimeResponse time) {

    public static ReservationResponseDto from(Reservation reservation) {
        ReservationTimeResponse reservationTimeResponse = ReservationTimeResponse.from(
                reservation.getReservationTime());

        return new ReservationResponseDto(reservation.getId(),
                reservation.getName(),
                reservation.getReservationDate(),
                reservationTimeResponse);
    }
}
