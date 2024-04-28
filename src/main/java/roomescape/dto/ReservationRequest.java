package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalDate;

public record ReservationRequest(LocalDate date, String name, long timeId) {
    public Reservation toReservation(long id, ReservationTime reservationTime) {
        return new Reservation(id, name, date, reservationTime);
    }
}
