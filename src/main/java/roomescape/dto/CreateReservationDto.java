package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.entity.Reservation;

public record CreateReservationDto(String name, LocalDate date, LocalTime time) {

    public Reservation toEntity(Long id) {
        return new Reservation(id, name, date, time);
    }
}
