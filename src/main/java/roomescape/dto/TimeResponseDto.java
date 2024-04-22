package roomescape.dto;

import roomescape.domain.Time;

public class TimeResponseDto {

    private final Long id;
    private final String startAt;

    public TimeResponseDto(final Long id, final String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static TimeResponseDto from(final Time time) {
        return new TimeResponseDto(time.getId(), time.getStartAt().toString());
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
