package roomescape.dto.request;

import java.time.LocalDate;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationCreateRequest(
        String name,
        LocalDate date,
        long timeId
) {
    public Reservation toReservation(ReservationTime reservationTime) {
        return new Reservation(Name.from(name), date, reservationTime);
    }
}
