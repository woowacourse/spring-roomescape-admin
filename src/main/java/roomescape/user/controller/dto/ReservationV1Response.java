package roomescape.user.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.user.domain.Reservation;
import roomescape.user.domain.ReservationTime;

public record ReservationV1Response(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {

    public static ReservationV1Response of(final Reservation reservation, final ReservationTime reservationTime) {
        return new ReservationV1Response(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservationTime.getStartAt()
        );
    }
}
