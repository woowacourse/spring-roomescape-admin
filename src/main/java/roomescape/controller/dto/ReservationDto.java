package roomescape.controller.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;

public record ReservationDto(
        long id,
        String name,
        LocalDate date,
        ReservationTimeResponseDto time
) {
    public ReservationDto(final Reservation reservation) {
        this(reservation.getId(), reservation.getName(), reservation.getDate(),
                new ReservationTimeResponseDto(reservation.getReservationTime()));
    }
}
