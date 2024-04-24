package roomescape.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record ReservationTime(Long id, LocalTime startAt) {

    public ReservationTime of(Long id, ReservationTime reservationTime) {
        return new ReservationTime(id, reservationTime.startAt);
    }

    public String startAt(DateTimeFormatter formatter) {
        return startAt.format(formatter);
    }
}
