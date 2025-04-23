package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain_entity.Reservation;

public record ReservationRequestDto(String name, LocalDate date, LocalTime time) {

    public Reservation toEntity() {
        return new Reservation(
                name, date, time
        );
    }
}
