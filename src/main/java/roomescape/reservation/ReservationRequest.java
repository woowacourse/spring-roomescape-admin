package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public ReservationRequest(final String name, final LocalDate date, final LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation toVo(final Long id) {
        return new Reservation(id, name, date, time);
    }
}
