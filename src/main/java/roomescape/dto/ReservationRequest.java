package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.strategy.ReservationDateStrategy;

public record ReservationRequest(String name, LocalDate date, long timeId) {

    public Reservation from(ReservationTime reservationTime, ReservationDateStrategy strategy) {
        return Reservation.of(this.name, this.date, reservationTime, strategy);
    }
}
