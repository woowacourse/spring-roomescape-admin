package roomescape.reservation.presentation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservation.repository.ReservationEntity;

public record ReservationRequest(
        String name,
        LocalDate date,
        LocalTime time
) {
    public ReservationEntity to() {
        return ReservationEntity.of(
                name,
                date,
                time
        );
    }
}
