package roomescape.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.id(), reservation.name(), reservation.date(), reservation.time());
    }
}
