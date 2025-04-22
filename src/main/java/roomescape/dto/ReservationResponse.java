package roomescape.dto;

import java.time.LocalDate;
import java.util.List;
import roomescape.domain.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeResponse time
) {

    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                ReservationTimeResponse.from(reservation.reservationTime())
        );
    }

    public static List<ReservationResponse> from(final List<Reservation> reservations) {
        return reservations.stream().map(ReservationResponse::from).toList();

    }
}
