package roomescape.reservation.dto;

import java.util.List;
import roomescape.reservation.domain.Reservation;

public record ReservationsResponse(List<ReservationResponse> reservationResponse) {
    public static ReservationsResponse from(List<Reservation> reservations) {
        List<ReservationResponse> reservationsResponse = reservations.stream()
                .map(ReservationResponse::from)
                .toList();
        return new ReservationsResponse(reservationsResponse);
    }
}
