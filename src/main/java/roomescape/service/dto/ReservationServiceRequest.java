package roomescape.service.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationServiceRequest(
        String name,
        LocalDate date,
        Long timeId
) {

        public Reservation toReservation(ReservationTime reservationTime) {
                return new Reservation(name, date, reservationTime);
        }
}
