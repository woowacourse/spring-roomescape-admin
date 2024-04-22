package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public class ReservationTime {

    private final long id;
    @JsonFormat(pattern = "HH:mm") // 여기 붙이면 언제 format이 되는거지?
    private final LocalTime startAt;

    public ReservationTime(final long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
