package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public class ReservationTime {

    private final Long id;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
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
