package roomescape.dto.reservation;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationRequest(
        String name,
        LocalDate date,
        Long timeId
) {

    public Reservation toReservation() {
        ReservationTime reservationTime = new ReservationTime(timeId, null);

        return new Reservation(null, name, date.toString(), reservationTime);
    }
}
