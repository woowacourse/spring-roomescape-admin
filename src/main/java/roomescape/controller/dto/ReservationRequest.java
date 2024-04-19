package roomescape.controller.dto;

import roomescape.domain.Reservation2;
import roomescape.domain.ReservationTime;

public record ReservationRequest(
        String date,
        String name,
        Long timeId) {

    public Reservation2 toEntity() {
        return new Reservation2(null, date, name, new ReservationTime(timeId, null));
    }
}
