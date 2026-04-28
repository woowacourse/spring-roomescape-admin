package roomescape.response;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ReservationResponse(long id, String name, LocalDate date, LocalTime time) {
    public static List<ReservationResponse> from(List<Reservation> reservations) {
        return reservations.stream().map(ReservationResponse::from).toList();
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.dateTime().toLocalDate(),
                reservation.dateTime().toLocalTime()
        );
    }
}
