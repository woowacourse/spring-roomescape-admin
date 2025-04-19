package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.model.Reservation;

public record ReservationRequest(
        String name,
        String date,
        String time
) {
    public Reservation toEntity(Long id) {
        return new Reservation(
                id,
                name,
                LocalDate.parse(date),
                LocalTime.parse(time)
        );
    }
}
