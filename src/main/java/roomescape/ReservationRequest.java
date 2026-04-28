package roomescape;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
