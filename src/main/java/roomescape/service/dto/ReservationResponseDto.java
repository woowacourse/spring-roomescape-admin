package roomescape.service.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;

public record ReservationResponseDto(
        long id,
        String name,
        LocalDate date,
        ReservationTimeResponseDto time
) {
    public ReservationResponseDto(final Reservation reservation) {
        this(reservation.getId(), reservation.getName(), reservation.getDate(),
                new ReservationTimeResponseDto(reservation.getReservationTime()));
    }
}
