package roomescape.controller.dto.request;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationCreateRequest(String name, LocalDate date, Long timeId) {

    public Reservation toReservation(ReservationTime reservationTime) {
        return new Reservation(null, name, date, reservationTime);
    }
}
