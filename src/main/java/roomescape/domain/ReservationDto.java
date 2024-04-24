package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(String name, LocalDate date, Long timeId) {
    public Reservation toEntity(Long id, TimeSlot time) {
        return new Reservation(id, name, date, time);
    }
}
