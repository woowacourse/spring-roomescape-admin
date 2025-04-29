package roomescape.reservation.web.dto;

import java.time.LocalDate;
import roomescape.reservation.Reservation;

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
