package roomescape.dto;

import roomescape.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {

    public Reservation toEntity(Long id) {
        return new Reservation(id, name, LocalDateTime.of(date, time));
    }
}
