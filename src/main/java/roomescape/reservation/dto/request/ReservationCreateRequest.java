package roomescape.reservation.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservation.domain.Reservation;

public record ReservationCreateRequest(
        String name,
        LocalDate date,
        LocalTime time
) {
    public Reservation toReservation() {
        return new Reservation(name, date, time);
    }
}
