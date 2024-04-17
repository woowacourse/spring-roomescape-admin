package roomescape.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import roomescape.db.domain.Reservation;

public record ReservationResponse(long id, String name, LocalDate date, @JsonFormat(pattern = "HH:mm") LocalTime time) {

    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
                reservation.getTime());
    }

    public static List<ReservationResponse> fromReservations(final List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }
}
