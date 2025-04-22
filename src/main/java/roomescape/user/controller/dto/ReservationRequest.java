package roomescape.user.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.user.domain.Reservation;

public record ReservationRequest(
        String name,
        LocalDate date,
        LocalTime time
) {

    public Reservation toReservation() {
        return new Reservation(name, date, time);
    }
}
