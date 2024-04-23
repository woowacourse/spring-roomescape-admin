package roomescape.dao;

import java.time.LocalTime;

public class ReservationTimeDao {
    private final LocalTime startAt;

    public ReservationTimeDao(LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
