package roomescape.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationCreateRequest(
        String name,
        LocalDate date,
        LocalTime time
) {
    public Reservation toEntity(long id) {
        return new Reservation(id, name, date, time);
    }
}
