package roomescape.reservation.controller.dto;

import java.time.LocalDate;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.controller.dto.ReservationTimeResponse;
import roomescape.reservationtime.domain.ReservationTime;

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
