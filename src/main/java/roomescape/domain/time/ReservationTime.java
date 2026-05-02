package roomescape.domain.time;

import java.time.LocalTime;

public class ReservationTime {
    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        validateTime(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private void validateTime(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("[ERROR] 시작 시간은 필수입니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
