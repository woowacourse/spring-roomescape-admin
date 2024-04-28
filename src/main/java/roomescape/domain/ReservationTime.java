package roomescape.domain;

import java.time.LocalTime;

public record ReservationTime(Long id, LocalTime startAt) {

    public ReservationTime(Long id, String startAt) {
        this(id, LocalTime.parse(startAt));
    }
}
