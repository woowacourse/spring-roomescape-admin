package roomescape.reservation.controller.response;

import java.time.format.DateTimeFormatter;
import java.util.List;
import roomescape.reservation.domain.Reservation;
import roomescape.time.controller.response.ReservationTimeResponse;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        ReservationTimeResponse time
) {
    public static ReservationResponse from(Reservation reservation) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return new ReservationResponse(
                reservation.getId(),
                reservation.getReserverName(),
                reservation.getDate().toString(),
                new ReservationTimeResponse(
                        reservation.getTimeId(),
                        reservation.getTime().format(timeFormatter)
                )
        );
    }

    public static List<ReservationResponse> from(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }
}
