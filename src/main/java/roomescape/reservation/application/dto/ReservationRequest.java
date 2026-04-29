package roomescape.reservation.application.dto;

import java.sql.Date;
import java.time.LocalDate;
import roomescape.reservation.repository.ReservationEntity;

public record ReservationRequest(
        String name,
        LocalDate date,
        Long timeId
) {
    public ReservationEntity to() {
        return ReservationEntity.of(
                name,
                Date.valueOf(date),
                timeId
        );
    }
}
