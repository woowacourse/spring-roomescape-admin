package roomescape.reservation.dto;

import java.time.LocalDate;
import roomescape.ReservationTime;
import roomescape.reservation.domain.Reservation;
import roomescape.response.ReservationTimeResponse;

public record ReservationResponse(
        long id,
        String name,
        LocalDate date,
        ReservationTimeResponse reservationTime
) {
    public ReservationResponse(Reservation reservation, ReservationTime reservationTime) {
        this(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                new ReservationTimeResponse(reservationTime)
        );
    }
}
