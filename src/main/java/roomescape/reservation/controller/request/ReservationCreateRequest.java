package roomescape.reservation.controller.request;

import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.ReservationTime;

public record ReservationCreateRequest(
        String name,
        LocalDate date,
        Long timeId
) {
    public Reservation to() {
        return new Reservation(name, date, new ReservationTime(timeId));
    }
}
