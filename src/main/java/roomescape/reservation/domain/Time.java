package roomescape.reservation.domain;

import java.time.LocalTime;
import lombok.Getter;

@Getter
public class Time {
    private final Long id;
    private final LocalTime startAt;

    public Time(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }
}
