package roomescape.dto;

import java.time.LocalTime;

public record ReservationTime(
        long id,
        LocalTime startAt
) {
    public static ReservationTime of(final long id, final String start_at) {
        return new ReservationTime(id, LocalTime.parse(start_at));
    }

    public static ReservationTime parse(final String time) {
        return LocalTime.parse(time);
    }
}
