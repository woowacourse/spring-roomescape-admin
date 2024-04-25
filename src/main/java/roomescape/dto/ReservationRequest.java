package roomescape.dto;

import java.time.LocalDate;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

public record ReservationRequest(String name, LocalDate date, long timeId) {

    public Reservation toReservation(ReservationTime reservationTime) {
        return new Reservation(name, date, reservationTime);
    }
}
