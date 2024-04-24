package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationRequest(
        String name,
        String date,
        Long timeId
) {

    public Reservation toEntity(ReservationTime reservationTime) {
        return new Reservation(
                name,
                LocalDate.parse(date),
                reservationTime
        );
    }
}
