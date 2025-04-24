package roomescape.reservationtime.domain;

import java.time.LocalTime;
import roomescape.common.domain.Cacheable;

public class ReservationTime implements Cacheable {
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
