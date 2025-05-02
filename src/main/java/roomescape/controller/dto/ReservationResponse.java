package roomescape.controller.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;

public record ReservationResponse(
        long id,
        String name,
        LocalDate date,
        ReservationTimeResponse reservationTimeResponse
) {

    public static ReservationResponse toDto(final Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(),
                reservation.getDate(),
                ReservationTimeResponse.toDto(reservation.getTime()));
    }
}
