package roomescape.domain;

import java.time.LocalTime;

import roomescape.utils.Parser;

public class ReservationTime {
    private final LocalTime startAt;

    public ReservationTime(LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTime(String startAt) {
        this.startAt = Parser.parseTime(startAt);
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
