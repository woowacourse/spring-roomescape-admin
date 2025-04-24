package roomescape.time.dto;

import java.time.LocalTime;
import java.util.Objects;

public record TimeRequest(LocalTime startAt) {

    public TimeRequest{
        Objects.requireNonNull(startAt);
    }
}
