package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {
    private long id;
    private LocalTime startAt;

    public ReservationTime(long id, LocalTime startAt) throws NullPointerException {
        validate(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private void validate(LocalTime startAt) {
        if(startAt == null) {
            throw new NullPointerException();
        }
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
