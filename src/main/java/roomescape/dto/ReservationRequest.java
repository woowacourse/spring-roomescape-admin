package roomescape.dto;

import java.time.LocalDate;
import roomescape.Reservation;
import roomescape.ReservationTime;

public record ReservationRequest(String name, LocalDate date, long timeId) {

    public Reservation toReservation(ReservationTime reservationTime) {
        return new Reservation(name, date, reservationTime);
    }
}
