package roomescape.reservation.repository.dto;

import java.time.LocalDate;
import roomescape.reservation.repository.ReservationEntity;
import roomescape.time.application.dto.TimeInfo;
import roomescape.time.repository.TimeEntity;

public record Reservation(
        Long id,
        String name,
        LocalDate date,
        TimeInfo timeInfo
) {
    public static Reservation from(
            ReservationEntity reservationEntity,
            TimeEntity timeEntity
    ) {
        return new Reservation(
                reservationEntity.id(),
                reservationEntity.name(),
                reservationEntity.date(),
                TimeInfo.from(timeEntity)
        );
    }
}
