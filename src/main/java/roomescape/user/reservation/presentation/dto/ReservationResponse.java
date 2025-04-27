package roomescape.user.reservation.presentation.dto;

import java.time.LocalDate;
import roomescape.user.reservation.domain.Reservation;
import roomescape.user.reservation.domain.ReservationTime;

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
