package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;

public record ReservationRequest(
        String name,
        LocalDate date,
        long timeId
) {
    public Reservation toEntity() {
        return Reservation.of(name, date, timeId);
    }
}
