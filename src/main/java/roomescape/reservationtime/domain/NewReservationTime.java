package roomescape.reservationtime.domain;

import java.time.LocalTime;

public class NewReservationTime {
    private final Long id;
    private final LocalTime time;

    public NewReservationTime(final Long id, final LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
