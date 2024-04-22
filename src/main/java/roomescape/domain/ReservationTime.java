package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {
    private final LocalTime value;

    public ReservationTime(final LocalTime value) {
        this.value = value;
    }

    public LocalTime getValue() {
        return value;
    }
}
