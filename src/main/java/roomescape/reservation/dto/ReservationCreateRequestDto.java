package roomescape.reservation.dto;

import roomescape.reservation.Reservation;
import roomescape.reservation.time.ReservationTime;

public record ReservationCreateRequestDto(
        String name,
        String date,
        Long timeId
) {
    public Reservation toEntity(ReservationTime reservationTime) {
        return Reservation.of(
                null,
                name(),
                date(),
                reservationTime
        );
    }
}
