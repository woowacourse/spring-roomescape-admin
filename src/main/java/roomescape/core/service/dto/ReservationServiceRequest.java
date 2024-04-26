package roomescape.core.service.dto;

import java.time.LocalDate;
import roomescape.core.domain.Reservation;
import roomescape.core.domain.ReservationTime;

public record ReservationServiceRequest(
        String name,
        LocalDate date,
        Long timeId
) {

        public Reservation toReservation(ReservationTime reservationTime) {
                return new Reservation(name, date, reservationTime);
        }
}
