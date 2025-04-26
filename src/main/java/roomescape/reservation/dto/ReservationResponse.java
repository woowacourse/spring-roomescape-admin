package roomescape.reservation.dto;

import java.time.LocalDate;
import roomescape.reservation.Reservation;
import roomescape.time.dto.ReservationTimeResponse;

public record ReservationResponse(
        long id,
        String name,
        LocalDate date,
        ReservationTimeResponse time
) {

    public static ReservationResponse createResponse(final Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                ReservationTimeResponse.createResponse(reservation.reservationTime())
        );
    }
}
