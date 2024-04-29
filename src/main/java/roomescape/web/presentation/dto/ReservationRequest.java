package roomescape.web.presentation.dto;

import java.time.LocalDate;
import roomescape.core.domain.Reservation;
import roomescape.core.domain.ReservationTime;

public record ReservationRequest(
        LocalDate date,
        String name,
        Long timeId) {
    public Reservation toReservation(ReservationTime reservationTime) {
        return new Reservation(name, date, reservationTime);
    }
}
