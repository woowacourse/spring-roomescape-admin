package roomescape.time.domain;

import java.time.LocalTime;

public class ReservationTime {
    private final Long id;
    private final LocalTime localTime;

    public ReservationTime(final Long id, final LocalTime localTime) {
        this.id = id;
        this.localTime = localTime;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }
}
