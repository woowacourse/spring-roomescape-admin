package roomescape.dto.response;

import java.util.List;
import roomescape.domain.Reservations;

public record ReservationResponses(List<ReservationResponse> reservationResponses) {

    public static ReservationResponses fromEntity(Reservations reservations) {
        final List<ReservationResponse> responses = reservations.getReservations().stream()
                .map(ReservationResponse::fromEntity).toList();

        return new ReservationResponses(responses);
    }
}
