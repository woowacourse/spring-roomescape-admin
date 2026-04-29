package roomescape.reservation.presentation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.reservation.dao.ReservationEntity;

public record ReservationRequest(
        String name,
        LocalDate date,
        LocalTime time
) {
    public ReservationEntity to() {
        return new ReservationEntity(
                name,
                LocalDateTime.of(date, time)
        );
    }
}
