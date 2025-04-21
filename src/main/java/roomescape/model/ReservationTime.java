package roomescape.model;

import java.time.LocalTime;

public class ReservationTime {
    private final LocalTime startAt;

    public ReservationTime(LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
