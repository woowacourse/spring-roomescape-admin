package roomescape.reservationTime.model;

import java.time.LocalTime;

public class ReservationTime {

    private final LocalTime startAt;

    public ReservationTime(final LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
