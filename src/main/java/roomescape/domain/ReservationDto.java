package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(String name, LocalDate date, LocalTime time) {
    public Reservation toEntity(Long id){
        return new Reservation(id, name, date, time);
    }
}
