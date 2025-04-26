package roomescape.reservation.dto;

import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationTime.domain.ReservationTime;

public record ReservationRequest(String name, LocalDate date, Long timeId) {
    public Reservation createReservation(ReservationTime reservationTime) {
        return new Reservation(null, name, date, reservationTime);
    }
}
