package roomescape.controller.reservation;

import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.time.LocalDate;

public record ReservationRequest(String name, LocalDate date, Long timeId) {
    public Reservation toDomain() {
        return new Reservation(
                null,
                name,
                date,
                new ReservationTime(timeId, null)
        );
    }
}
