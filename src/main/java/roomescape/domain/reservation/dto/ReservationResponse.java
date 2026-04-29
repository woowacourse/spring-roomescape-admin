package roomescape.domain.reservation.dto;

import java.time.LocalDate;
import roomescape.domain.reservation.Reservation;

public record ReservationResponse(
    Long id,
    String name,
    LocalDate date,
    ReservationsTimeResponse time
) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate(),
            ReservationsTimeResponse.from(reservation.getTime())
        );
    }
}
