package roomescape.reservation.model;

import java.time.LocalTime;

public class Time {

    private final long id;
    private final TimeDetails startAt;

    public Time(long id, LocalTime startAt) {
        this.id = id;
        this.startAt = new TimeDetails(startAt);
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt.startAt();
    }
}
