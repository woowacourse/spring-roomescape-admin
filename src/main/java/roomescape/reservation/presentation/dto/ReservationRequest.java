package roomescape.reservation.presentation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.reservation.domain.Reservation;

public record ReservationRequest(
        String name,
        LocalDate date,
        LocalTime time
) {
    public Reservation to() {
        return new Reservation(
                name,
                LocalDateTime.of(date, time)
        );
    }
}
