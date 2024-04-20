package roomescape.presentation.web.response;

import java.time.LocalDate;
import roomescape.domain.Reservation;

public record ReservationResponse(

        Long id,

        String name,

        LocalDate date,

        ReservationTimeResponse time
) {

    public ReservationResponse(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getName().value(),
                reservation.getDate(),
                new ReservationTimeResponse(reservation.getTime())
        );
    }
}
