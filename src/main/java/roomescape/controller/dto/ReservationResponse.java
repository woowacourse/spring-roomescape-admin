package roomescape.controller.dto;

import java.time.LocalDate;
import roomescape.service.domain.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeResponse reservationTimeResponse
) {

    public ReservationResponse(final Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                new ReservationTimeResponse(reservation.getTime())
        );
    }
}
