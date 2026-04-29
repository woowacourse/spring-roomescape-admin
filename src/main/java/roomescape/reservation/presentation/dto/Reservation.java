package roomescape.reservation.presentation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservation.repository.ReservationEntity;

public record Reservation(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public static Reservation from(ReservationEntity reservationEntity) {
        return new Reservation(
                reservationEntity.id(),
                reservationEntity.name(),
                reservationEntity.date().toLocalDate(),
                reservationEntity.time().toLocalTime()
        );
    }
}
