package roomescape.service.reservation.request;

import java.time.LocalDate;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

public record ReservationServiceRequest(
        String name,
        LocalDate date,
        Long timeId) {

    public Reservation toReservation(final ReservationTime time) {
        return Reservation.fromWithoutId(this.name, this.date, time);
    }

}
