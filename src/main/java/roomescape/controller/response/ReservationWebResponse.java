package roomescape.controller.response;

import java.time.LocalDate;
import roomescape.domain.Reservation;

public record ReservationWebResponse(
        long id,
        String name,
        LocalDate date,
        ReservationTimeWebResponse time
) {

    public ReservationWebResponse(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getName().value(),
                reservation.getDate(),
                new ReservationTimeWebResponse(reservation.getTime())
        );
    }
}
