package roomescape.reservation.controller.request;

import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.ReservationTime;

public record ReservationCreateRequest(
        String name,
        String date,
        Long timeId
) {
    public Reservation to() {
        return new Reservation(name, LocalDate.parse(date), new ReservationTime(timeId));
    }

    public Long getTimeId() {
        return timeId;
    }
}
