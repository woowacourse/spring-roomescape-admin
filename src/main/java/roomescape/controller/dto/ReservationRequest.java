package roomescape.controller.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationRequest(
        String date,
        String name,
        Long timeId) {

    public Reservation toEntity() {
        return new Reservation(null, date, name, new ReservationTime(timeId, null));
    }
}
