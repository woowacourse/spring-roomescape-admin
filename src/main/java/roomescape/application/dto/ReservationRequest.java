package roomescape.application.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationRequest(
        String name,
        LocalDate date,
        Long timeId
) {

    public Reservation toReservationWithNullId(ReservationTime reservationTime) {
        return new Reservation(
                null,
                name,
                date,
                reservationTime
        );
    }
}
