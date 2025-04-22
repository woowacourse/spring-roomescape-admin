package roomescape.time;

import java.time.LocalTime;
import java.util.Objects;

public record TimeRequest(LocalTime startAt) {

    public TimeRequest{
        Objects.requireNonNull(startAt);
    }
}
