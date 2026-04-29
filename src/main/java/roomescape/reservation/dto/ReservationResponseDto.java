package roomescape.reservation.dto;

import roomescape.reservation.Reservation;
import roomescape.reservation.time.ReservationTime;

public record ReservationResponseDto(
        Long id,
        String name,
        String date,
        ReservationTime time
) {
    public static ReservationResponseDto from(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
