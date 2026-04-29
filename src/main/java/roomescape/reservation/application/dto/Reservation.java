package roomescape.reservation.application.dto;

import java.time.LocalDate;
import roomescape.reservation.repository.ReservationEntity;
import roomescape.time.repository.TimeEntity;

public record Reservation(
        Long id,
        String name,
        LocalDate date,
        TimeEntity time
) {
    public static Reservation from(
            ReservationEntity reservationEntity,
            TimeEntity timeEntity
    ) {
        return new Reservation(
                reservationEntity.id(),
                reservationEntity.name(),
                reservationEntity.date().toLocalDate(),
                timeEntity
        );
    }
}
