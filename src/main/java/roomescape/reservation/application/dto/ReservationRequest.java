package roomescape.reservation.application.dto;

import java.time.LocalDate;
import roomescape.reservation.repository.ReservationEntity;

public record ReservationRequest(
        String name,
        LocalDate date,
        Long timeId
) {
    public ReservationEntity toReservationEntity() {
        return ReservationEntity.of(
                name,
                date,
                timeId
        );
    }
}
