package roomescape.reservation.dto;

import java.time.LocalDate;
import roomescape.reservation.Reservation;
import roomescape.time.dto.TimeResponse;

public record ReservationResponse(
        long id,
        String name,
        LocalDate date,
        TimeResponse time
) {

    public static ReservationResponse createResponse(final Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                TimeResponse.createResponse(reservation.time())
        );
    }
}
