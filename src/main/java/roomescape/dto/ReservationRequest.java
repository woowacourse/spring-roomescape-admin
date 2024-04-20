package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;
import roomescape.domain.strategy.ReservationDateStrategy;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {

    public Reservation from(ReservationDateStrategy strategy) {
        return Reservation.of(this.name, this.date, this.time, strategy);
    }
}
