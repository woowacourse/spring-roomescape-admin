package roomescape.user.controller.dto;

import java.time.LocalDate;
import roomescape.user.domain.Reservation;
import roomescape.user.domain.ReservationTime;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        ReservationTime time
) {

    public static ReservationResponse of(final Reservation reservation, final ReservationTime reservationTime) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservationTime
        );
    }
}
