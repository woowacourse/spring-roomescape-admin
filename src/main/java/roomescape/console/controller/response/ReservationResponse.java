package roomescape.console.controller.response;

import roomescape.core.domain.Reservation;
import roomescape.web.controller.response.ReservationTimeResponse;

import java.time.LocalDate;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeResponse time
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName().value(),
                reservation.getDate(),
                new ReservationTimeResponse(reservation.getId(), reservation.getTime().getStartAt()));
    }
}
