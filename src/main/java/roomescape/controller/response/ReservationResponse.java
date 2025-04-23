package roomescape.controller.response;

import roomescape.domain.Reservation;

public record ReservationResponse(Long id, String name, String date, ReservationTimeResponse time) {

    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().toString(),
                new ReservationTimeResponse(
                        reservation.getId(),
                        reservation.getTime().toString()
                )
        );
    }
}
