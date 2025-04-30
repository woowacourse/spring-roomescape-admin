package roomescape.dto;

import java.util.List;
import roomescape.model.Reservation;

public record ReservationResponse(long id, String name, String date, ReservationTimeResponse time) {

    private static ReservationResponse toResponse(final Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().toString(),
                new ReservationTimeResponse(reservation.getTimeId(),
                        String.valueOf(reservation.getTimeStartAt()))
        );
    }

    public static List<ReservationResponse> toResponses(final List<Reservation> reservations) {
        return reservations.stream().map(ReservationResponse::toResponse).toList();
    }
}
