package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain_entity.Id;
import roomescape.domain_entity.Reservation;

public record ReservationRequestDto(String name, LocalDate date, LocalTime time) {

    public Reservation toReservationWith(long id) {
        return new Reservation(
                new Id(id), name, date, time
        );
    }
}
