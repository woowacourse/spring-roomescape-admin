package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import roomescape.domain.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {

    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                reservation.time()
        );
    }

    public static List<ReservationResponse> from(final List<Reservation> reservations) {
        return reservations.stream().map(ReservationResponse::from).toList();

    }
}
