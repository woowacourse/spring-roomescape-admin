package roomescape.time;

import java.time.LocalTime;
import java.util.Objects;

public record ReservationTime(Long id, LocalTime startAt) {

    public ReservationTime {
        Objects.requireNonNull(startAt);
    }

    @Override
    public Long id(){
        Objects.requireNonNull(id);
        return id;
    }
}
