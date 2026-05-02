package roomescape.reservation;

import java.time.LocalDate;

import roomescape.reservationtime.ReservationTimeResponse;


public record ReservationResponse(long id, String name, LocalDate date, ReservationTimeResponse time) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                ReservationTimeResponse.from(reservation.time())
        );
    }
}

