package roomescape.controller.reservation;

import roomescape.controller.time.TimeResponse;
import roomescape.entity.Reservation;

public record ReservationResponse(Long id, String name, String date, TimeResponse time) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.formatDate(),
                TimeResponse.from(reservation.time())
        );
    }
}
