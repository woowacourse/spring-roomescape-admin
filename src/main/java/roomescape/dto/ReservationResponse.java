package roomescape.dto;

import roomescape.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ReservationResponse(long id,
                                  String name,
                                  LocalDate date,
                                  LocalTime time) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
    }

    public static List<ReservationResponse> from(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }
}
