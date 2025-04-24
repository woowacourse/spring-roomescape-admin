package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;

public record ReservationResponse(
        long id,
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
