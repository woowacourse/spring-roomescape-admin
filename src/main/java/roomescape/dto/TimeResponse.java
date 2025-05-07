package roomescape.dto;

import roomescape.domain.Time;

public record TimeResponse(Long id, String startAt) {

    public static TimeResponse from(final Time time) {
        return new TimeResponse(time.getId(), time.getStartAt());
    }
}
