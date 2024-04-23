package roomescape.reservationtime;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}