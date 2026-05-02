package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Reservation;

public record ReservationResponse(Long id, String name, String date, ReservationTimeResponse time) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
            reservation.id(),
            reservation.name(),
            reservation.date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            ReservationTimeResponse.from(reservation.time())
        );
    }
}
