package roomescape.reservationtime.domain;

import java.time.LocalTime;

public class ReservationTime {
    private final LocalTime startAt;

    public ReservationTime(final LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTime(final String startAt) {
        this.startAt = LocalTime.parse(startAt);
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
