package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationRequest(
        String name,
        String date,
        String time
) {

    public Reservation toReservation() {
        LocalDateTime dateTime = LocalDateTime.of(
                LocalDate.parse(this.date),
                LocalTime.parse(this.time)
        );

        return new Reservation(
                name,
                dateTime
        );
    }
}
