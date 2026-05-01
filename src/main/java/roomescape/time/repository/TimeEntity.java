package roomescape.time.repository;

import java.time.LocalTime;

public record TimeEntity(
        Long id,
        LocalTime startAt
) {
    public static TimeEntity of(LocalTime startAt) {
        return new TimeEntity(null, startAt);
    }

    public TimeEntity updateId(long id) {
        return new TimeEntity(id, this.startAt);
    }
}
