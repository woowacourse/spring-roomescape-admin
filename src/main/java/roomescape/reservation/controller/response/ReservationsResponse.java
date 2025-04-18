package roomescape.reservation.controller.response;

import java.util.List;
import roomescape.reservation.domain.Reservations;

public record ReservationsResponse(List<ReservationResponse> reservationResponses) {
    public static ReservationsResponse from(Reservations reservations) {
        List<ReservationResponse> reservationResponses = reservations.getReservations()
                .stream()
                .map(ReservationResponse::from)
                .toList();

        return new ReservationsResponse(reservationResponses);
    }
}
