package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationSaveRequest(
        String name,
        LocalDate date,
        Long timeId
) {

    public Reservation toEntity(ReservationTime reservationTime) {
        return new Reservation(name, date, reservationTime);
    }
}
