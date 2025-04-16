package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequestDto(
        String name,
        LocalDate date,
        LocalTime time
) {

    public Reservation toEntity(int id) {
        return new Reservation(id, name, date, time);
    }
}
