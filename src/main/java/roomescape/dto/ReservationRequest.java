package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

public record ReservationRequest(String name, LocalDate date, Long timeId) {

    public Reservation toEntity() {
        return new Reservation(name, date, new Time(timeId, null));
    }
}
