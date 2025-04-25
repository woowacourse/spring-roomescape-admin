package roomescape.enttity.ReservationTime;

import java.time.LocalTime;

public class ReservationTime {
    private final Long id;
    private final LocalTime start_at;

    public ReservationTime(final Long id, final LocalTime start_at) {
        this.id = id;
        this.start_at = start_at;
    }

    public LocalTime getStart_at() {
        return start_at;
    }

    public Long getId() {
        return id;
    }
}
