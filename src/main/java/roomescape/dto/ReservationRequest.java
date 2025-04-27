package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

public record ReservationRequest(String name, String date, Long timeId) {

    public Reservation toEntity() {
        return new Reservation(name, date, new Time(timeId, null));
    }
}
