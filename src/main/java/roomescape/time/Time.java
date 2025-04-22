package roomescape.time;

import java.time.LocalTime;
import java.util.Objects;

public record Time(Long id, LocalTime startAt) {

    public Time {
        Objects.requireNonNull(startAt);
    }

    public Time writeId(final Long id){
        return new Time(id, startAt);
    }
}
