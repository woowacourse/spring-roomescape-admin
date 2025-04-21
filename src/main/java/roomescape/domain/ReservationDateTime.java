package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationDateTime(LocalDateTime reservationTime) {

    public LocalDate getDate() {
        return reservationTime.toLocalDate();
    }

    public LocalTime getTime() {
        return reservationTime.toLocalTime();
    }
}
