package roomescape.dto;

import java.time.LocalDate;
import roomescape.entity.Reservation;

public record ReservationResponseDto(
    long id,
    String name,
    LocalDate date,
    ReservationTimeResponseDto time
) {

    public static ReservationResponseDto from(final Reservation reservation, final ReservationTimeResponseDto reservationTimeResponseDto) {
        return new ReservationResponseDto(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate(),
            reservationTimeResponseDto);
    }
}
