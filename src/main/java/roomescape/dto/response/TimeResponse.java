package roomescape.dto.response;

import java.time.LocalTime;
import roomescape.domain.Time;

public record TimeResponse(Long id, LocalTime startAt) {
    public static TimeResponse toDto(Time time) {
        return new TimeResponse(time.getId(), time.getStartAt());
    }
}
