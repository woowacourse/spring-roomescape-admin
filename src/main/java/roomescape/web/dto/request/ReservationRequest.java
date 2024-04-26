package roomescape.web.dto.request;

import java.time.LocalDate;
import roomescape.web.domain.Reservation;
import roomescape.web.domain.ReservationTime;

public record ReservationRequest(String name, LocalDate date, Long timeId) {

    public Reservation toEntity(ReservationTime reservationTime) {
        return new Reservation(null, name, date, reservationTime);
    }
}
