package roomescape.time;

import java.time.LocalTime;
import java.util.Objects;

public record Time(Long id, LocalTime startAt) {

    public Time {
        Objects.requireNonNull(startAt);
    }

    @Override
    public Long id(){
        Objects.requireNonNull(id);
        return id;
    }
}
