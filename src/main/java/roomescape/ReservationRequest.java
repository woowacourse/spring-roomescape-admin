package roomescape;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationRequest(
        String name,
        String date,
        String time
) {

    public Reservation toEntity(Long id) {
        LocalDateTime dateTime = LocalDateTime.of(
                LocalDate.parse(this.date),
                LocalTime.parse(this.time)
        );

        return new Reservation(
                id,
                name,
                dateTime
        );
    }
}
