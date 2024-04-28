package roomescape.domain;

import java.time.LocalTime;

public class ReservationTime {
    private final Long id;
    private final LocalTime startTime;

    public ReservationTime(Long id, LocalTime startTime) {
        this.id = id;
        this.startTime = startTime;
    }

    public ReservationTime(Long id, String startTime) {
        this(id, LocalTime.parse(startTime));
    }

    public ReservationTime(LocalTime startTime) {
        this(null, startTime);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
}
