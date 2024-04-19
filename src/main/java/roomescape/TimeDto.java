package roomescape;

import java.time.LocalTime;

public class TimeDto {

    private final Long id;
    private final LocalTime startAt;

    public TimeDto(Long id, LocalTime startAt) {
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
