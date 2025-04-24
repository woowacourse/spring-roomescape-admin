package roomescape.reservation;

import java.time.LocalDate;
import roomescape.reservationTime.ReservationTime;

public record Reservation(Long id, String name, LocalDate date, ReservationTime time) {

    public boolean isSameId(Long targetId) {
        return id.equals(targetId);
    }
}
