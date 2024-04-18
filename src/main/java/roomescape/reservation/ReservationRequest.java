package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {

    public Reservation toVo(final Long id) {
        return new Reservation(id, name, date, time);
    }
}
