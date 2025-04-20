package roomescape.reservation.controller.response;

import java.time.format.DateTimeFormatter;
import java.util.List;
import roomescape.reservation.domain.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        String time
) {
    public static ReservationResponse from(Reservation reservation) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return new ReservationResponse(
                reservation.getId(),
                reservation.getReserverName(),
                reservation.getDate().toString(),
                reservation.getTime().format(timeFormatter)
        );
    }

    public static List<ReservationResponse> from(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }
}
