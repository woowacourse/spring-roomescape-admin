package roomescape.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record ReservationTime(Long id, LocalTime startAt) {

    public static ReservationTime from(Long id) {
        return new ReservationTime(id, null);
    }

    public static ReservationTime of(Long id, ReservationTime reservationTime) {
        return new ReservationTime(id, reservationTime.startAt);
    }

    public String startAt(DateTimeFormatter formatter) {
        return startAt.format(formatter);
    }
}
