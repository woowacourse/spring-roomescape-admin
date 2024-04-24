package roomescape.admin.reservation.controller.dto.request;

import java.time.LocalDate;
import roomescape.admin.reservation.entity.Reservation;
import roomescape.admin.reservation.entity.ReservationTime;

public record ReservationRequest(String name, LocalDate date, Long timeId) {
    public Reservation toReservation() {
        ReservationTime reservationTime = new ReservationTime(timeId, null);
        return new Reservation(name, date, reservationTime);
    }
}
