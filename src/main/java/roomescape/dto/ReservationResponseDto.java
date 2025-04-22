package roomescape.dto;

import java.time.LocalDate;
import roomescape.entity.Reservation;

public record ReservationResponseDto(
        Long id,
        String name,
        LocalDate date,
        Long timeId
) {

    public static ReservationResponseDto toDto(final Reservation reservation) {
        return new ReservationResponseDto(reservation.id(), reservation.name(),
                reservation.date(),
                reservation.time().id());
    }
}
