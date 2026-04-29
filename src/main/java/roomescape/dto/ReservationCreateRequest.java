package roomescape.dto;

import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.time.LocalDate;

public record ReservationCreateRequest(
        String name,
        LocalDate date,
        Long timeId
) {
    public Reservation toEntity(ReservationTime reservationTime) {
        return new Reservation(null, name, date, reservationTime);
    }
}
