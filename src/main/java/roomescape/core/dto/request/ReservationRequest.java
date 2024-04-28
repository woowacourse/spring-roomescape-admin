package roomescape.core.dto.request;

import java.time.LocalDate;
import roomescape.core.domain.Reservation;
import roomescape.core.domain.ReservationTime;

public record ReservationRequest(String name, LocalDate date, Long timeId) {

    public Reservation toEntity(ReservationTime reservationTime) {
        return new Reservation(null, name, date, reservationTime);
    }
}
