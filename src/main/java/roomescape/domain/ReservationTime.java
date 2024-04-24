package roomescape.domain;

import java.time.LocalTime;

public record ReservationTime(
        long id,
        LocalTime startAt
) {
    public static ReservationTime of(final long id, final String start_at) {
        return new ReservationTime(id, LocalTime.parse(start_at));
    }

    public static ReservationTime from(final long id) {
        return new ReservationTime(id, null);
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
