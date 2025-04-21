package roomescape.reservation.controller.request;

import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;

public record ReservationCreateRequest(
        String name,
        String date,
        Long timeId
) {
    public Reservation to() {
        return new Reservation(name, LocalDate.parse(date), null);
    }

    public Long getTimeId() {
        return timeId;
    }
}
