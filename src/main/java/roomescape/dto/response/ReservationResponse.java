package roomescape.dto.response;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.util.List;

public record ReservationResponse(
        long id,
        String name,
        LocalDate date,
        ReservationTimeResponse time
) {
    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(reservation.id(), reservation.name(), reservation.date(), ReservationTimeResponse.from(reservation.time()));
    }

    public static List<ReservationResponse> fromList(final List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }
}
