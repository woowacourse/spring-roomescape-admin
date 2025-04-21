package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record AddReservationDto(String name, LocalDate date, LocalTime time) {

    public Reservation toEntity() {
        return new Reservation(null, name,
                date, time);
    }
}

