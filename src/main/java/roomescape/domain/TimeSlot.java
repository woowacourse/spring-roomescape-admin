package roomescape.domain;

import java.time.LocalTime;

public class TimeSlot {
    private final Long id;
    private final LocalTime startAt;

    public TimeSlot(Long id, LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("올바르지 않은 예약 시간입니다.");
        }

        this.id = id;
        this.startAt = startAt;
    }

    public TimeSlot(Long id, String startAt) {
        this(id, LocalTime.parse(startAt));
    }

    public TimeSlot(String startAt) {
        this(null, startAt);
    }

    public boolean isTimeBeforeNow() {
        return startAt.isBefore(LocalTime.now());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
