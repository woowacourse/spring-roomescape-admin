package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationCreateDto(String name, LocalDate date, LocalTime time) {

    public Reservation toEntity(Long index) {
        return new Reservation(index, name, date, time);
    }
}
