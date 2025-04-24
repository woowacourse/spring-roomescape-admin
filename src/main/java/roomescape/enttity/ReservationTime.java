package roomescape.enttity;

import java.time.LocalTime;

public class ReservationTime {
    private final long id;
    private final LocalTime start_at;

    public ReservationTime(final long id, final LocalTime start_at) {
        this.id = id;
        this.start_at = start_at;
    }
}
