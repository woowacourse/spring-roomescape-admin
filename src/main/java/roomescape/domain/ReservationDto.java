package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(Long id, String name, String date, String time) {

    public Reservation toEntity(Long id) {
        return new Reservation(id, name, LocalDate.parse(date), LocalTime.parse(time));
    }
}
