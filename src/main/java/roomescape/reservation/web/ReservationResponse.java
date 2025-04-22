package roomescape.reservation.web;

import java.time.LocalDate;
import roomescape.reservation.Reservation;
import roomescape.time.web.ReservationTimeResponse;

public record ReservationResponse(
        long id,
        String name,
        LocalDate date,
        ReservationTimeResponse time
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getCustomerName(),
                reservation.getReservationDate(),
                new ReservationTimeResponse(reservation.getReservationTime())
        );
    }
}
