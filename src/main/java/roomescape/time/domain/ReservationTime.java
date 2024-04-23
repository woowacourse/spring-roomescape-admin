package roomescape.time.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationTime {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private final long id;
    private final LocalTime startAt;

    public ReservationTime(final long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(final long id, final String startAt) {
        this(id, LocalTime.parse(startAt, TIME_FORMAT));
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
