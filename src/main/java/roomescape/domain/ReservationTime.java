package roomescape.domain;

import java.time.LocalTime;

public record ReservationTime(
    Long id,
    LocalTime startAt
) {

    public ReservationTime withId(long id) {
        return new ReservationTime(id, startAt);
    }
}
