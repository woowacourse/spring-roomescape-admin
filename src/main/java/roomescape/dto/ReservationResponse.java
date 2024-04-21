package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import roomescape.domain.Reservation;

public record ReservationResponse(long id, String name, LocalDate date, LocalTime time) {

    public static ReservationResponse toResponse(long id, ReservationRequest reservationRequest) {
        return new ReservationResponse(
                id,
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time()
        );
    }

    public static List<ReservationResponse> toResponses(List<Reservation> reservations) {
        return reservations.stream()
                .map(reservation -> new ReservationResponse(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime()
                ))
                .toList();
    }
}
