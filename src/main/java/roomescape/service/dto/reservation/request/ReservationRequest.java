package roomescape.service.dto.reservation.request;

import java.time.LocalDate;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

public record ReservationRequest(
        String name,
        LocalDate date,
        Long timeId) {

    public Reservation toReservation(final ReservationTime time) {
        return Reservation.from(this.name, this.date, time);
    }

}
