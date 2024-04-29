package roomescape.core.service.request;

import roomescape.core.domain.Reservation;
import roomescape.core.domain.ReservationTime;

import java.time.LocalDate;

public record ReservationRequestDto(
        String name,
        LocalDate date,
        Long timeId
) {
    public Reservation toEntity(ReservationTime reservationTime) {
        return new Reservation(name, date, reservationTime);
    }
}
