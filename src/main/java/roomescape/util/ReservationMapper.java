package roomescape.util;

import roomescape.Reservation;
import roomescape.dto.ReservationResponse;

public class ReservationMapper {

    public static ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                reservation.time()
        );
    }
}
