package roomescape.time.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record ReservationTime(long id, LocalTime startAt) {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public ReservationTime(final long id, final String startAt) {
        this(id, LocalTime.parse(startAt, TIME_FORMAT));
    }
}
