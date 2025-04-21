package roomescape.domain;

import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {
    private final Long id;
    private final LocalTime time;

    public ReservationTime(final Long id, final LocalTime time) {
        this.id = Objects.requireNonNull(id, "예약 id는 null일 수 없습니다.");
        this.time = Objects.requireNonNull(time, "예약 시간은 null일 수 없습니다.");
    }

    public Long id() {
        return id;
    }

    public LocalTime time() {
        return time;
    }
}
