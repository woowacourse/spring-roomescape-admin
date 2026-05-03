package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {

    private Long id;
    private LocalTime startAt;

    private ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime create(LocalTime startAt) {
        return new ReservationTime(null, startAt);
    }

    public static ReservationTime withId(Long id, LocalTime startAt) {
        return new ReservationTime(id, startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
