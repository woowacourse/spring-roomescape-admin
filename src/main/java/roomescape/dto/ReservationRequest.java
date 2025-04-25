package roomescape.dto;

import java.time.LocalDate;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

public record ReservationRequest(String name, String date, Long timeId) {

    public Reservation toReservation() {
        return new Reservation(name, LocalDate.parse(date), new ReservationTime(timeId));
    }
}
