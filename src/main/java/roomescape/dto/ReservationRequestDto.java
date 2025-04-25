package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain_entity.Id;
import roomescape.domain_entity.Reservation;
import roomescape.domain_entity.ReservationTime;

public record ReservationRequestDto(String name, LocalDate date, long timeId) {

    public Reservation toReservation() {
        return new Reservation(
                name, date, new ReservationTime(new Id(timeId))
        );
    }
}
