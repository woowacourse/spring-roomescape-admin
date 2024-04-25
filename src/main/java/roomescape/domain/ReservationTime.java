package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {

    private int id;
    private LocalTime startAt;

    public ReservationTime() {
    }

    public ReservationTime(int id) {
        this(id, null);
    }

    public ReservationTime(int id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public int getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
