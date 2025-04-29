package roomescape.dto;

import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationTime;

import java.time.LocalDate;

public record ReservationRequest(String name, LocalDate date, Long timeId) {
    public Reservation toReservation(ReservationTime reservationTime) {
        return new Reservation(null, name, date, reservationTime);
    }
}
