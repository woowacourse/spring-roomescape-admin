package roomescape.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationTime {
    private final static String TIME_FORMAT = "HH:mm";

    private final long id;
    private final LocalTime time;

    public ReservationTime(long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public ReservationTime(long id, String time) {
        this(id, LocalTime.parse(time));
    }

    public long getId() {
        return id;
    }

    public String getTime() {
        return time.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }
}
