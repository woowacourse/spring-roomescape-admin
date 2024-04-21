package roomescape.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationTime {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = LocalTime.parse(startAt, TIME_FORMATTER);
    }

    public ReservationTime(Long id) {
        this.id = id;
        this.startAt = null;
    }

    public ReservationTime(String startAt) {
        this(null, startAt);
    }

    public Long id() {
        return id;
    }

    public String startAt() {
        return startAt.format(TIME_FORMATTER);
    }

}
