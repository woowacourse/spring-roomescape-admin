package roomescape.dto;

import java.time.LocalTime;

public record Time(
        long id,
        LocalTime startAt
) {
    public static Time of(final long id, final String start_at) {
        return new Time(id, LocalTime.parse(start_at));
    }
}
