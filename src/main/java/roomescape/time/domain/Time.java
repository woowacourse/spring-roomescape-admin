package roomescape.time.domain;

import java.time.LocalTime;

public class Time {

    private final Long id;
    private final LocalTime startAt;

    public Time(Long id, LocalTime startAt) {
        validateStartAt(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    public Time(LocalTime startAt) {
        this(null, startAt);
    }

    public Time(Long id, Time time) {
        this(id, time.startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public void validateStartAt(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("시간은 null일 수 없습니다.");
        }
    }
}
