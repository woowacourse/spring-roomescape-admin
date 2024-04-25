package roomescape.domain.time;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public class Time {

    private final Long id;

    // TODO: DTO 분리
    @JsonFormat(pattern = "HH:mm")
    private final LocalTime startAt;

    public Time(LocalTime startAt) {
        this(null, startAt);
    }

    public Time(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
