package roomescape.time.repository;

import java.sql.Time;
import java.time.LocalTime;

public record TimeEntity(
        Long id,
        Time startAt
) {
    public static TimeEntity of(LocalTime startAt) {
        return new TimeEntity(null, Time.valueOf(startAt));
    }

    public TimeEntity updateId(long id) {
        return new TimeEntity(id, this.startAt);
    }
}
