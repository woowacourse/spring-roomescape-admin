package roomescape.entity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record ReservationTime(Long id, LocalTime startAt) {

    public ReservationTime assignId(Long id) {
        return new ReservationTime(id, startAt);
    }

    public String formatTime() {
        return startAt.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
