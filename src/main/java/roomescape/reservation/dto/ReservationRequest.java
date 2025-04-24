package roomescape.reservation.dto;

import java.time.LocalDate;
import roomescape.reservation.Reservation;
import roomescape.reservationTime.ReservationTime;

public record ReservationRequest(String name, LocalDate date, Long timeId) {
    public Reservation createReservation() {
        ReservationTime reservationTime = new ReservationTime(timeId, null);
        return new Reservation(null, name, date, reservationTime);
    }
}
