package roomescape.reservation.dto;

import java.util.List;
import roomescape.reservation.Reservation;

public record AllReservationResponse(List<ReservationResponse> reservations) {
    public static AllReservationResponse from(List<Reservation> allResponse) {
        return new AllReservationResponse(allResponse.stream()
                .map(ReservationResponse::from)
                .toList());
    }
}
