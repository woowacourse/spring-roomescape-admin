package roomescape.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationResponse(String username, LocalDate when, LocalTime time) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.username(), reservation.date(), reservation.time());
    }
}
