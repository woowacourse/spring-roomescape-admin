package roomescape.reservation.dto;

import roomescape.reservation.entity.Reservation;
import roomescape.time.entity.ReservationTime;

public record ReservationResponseDto(
        long id,
        String name,
        String date,
        ReservationTime reservationTime
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
