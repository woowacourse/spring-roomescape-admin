package roomescape.time.entity;

import java.time.LocalTime;

public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    private ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime createNew(LocalTime startAt) {
        return new ReservationTime(null, startAt);
    }

    public static ReservationTime of(Long id, LocalTime startAt) { return  new ReservationTime(id, startAt); }

    public ReservationTime withId(Long id) {
        return new ReservationTime(id, startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }


}
