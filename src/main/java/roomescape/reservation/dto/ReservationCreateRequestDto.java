package roomescape.reservation.dto;

import roomescape.reservation.Reservation;
import roomescape.reservation.time.ReservationTime;

import java.time.LocalDate;

public record ReservationCreateRequestDto(
        String name,
        LocalDate date,
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
