package roomescape.dto.request;

import roomescape.dto.TimeData;

import java.time.LocalTime;

public record TimeCreateRequest(
        LocalTime startAt
) {

    public TimeData toData() {
        return new TimeData(startAt);
    }
}
