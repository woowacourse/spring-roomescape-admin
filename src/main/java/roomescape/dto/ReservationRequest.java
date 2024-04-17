package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationRequest(
        String name,
        LocalDate date,
        LocalTime time
) {

    public Reservation toReservation(Long id) {
        return new Reservation(id, name, LocalDateTime.of(date, time));
    }
}
