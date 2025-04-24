package roomescape.dto.request;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationRequest(
        String name,
        LocalDate date,
        Long timeId
) {

    public Reservation toReservation(Long id, ReservationTime reservationTime) {
        return new Reservation(
                id,
                name,
                date,
                reservationTime
        );
    }
}
