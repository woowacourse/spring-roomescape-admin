package roomescape.dto.response;

import roomescape.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                reservation.time()
        );
    }

    public static List<ReservationResponse> from(List<Reservation> reservations) {
        return reservations.stream()
                .map(reservation -> new ReservationResponse(
                                reservation.id(),
                                reservation.name(),
                                reservation.date(),
                                reservation.time()
                        )
                )
                .toList();
    }
}
