package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;

public record ReservationRequest(String name, LocalDate date, Long timeId) {
    public Reservation toReservation() {
        return new Reservation(name, date, timeId);
    }
}
