package roomescape.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.reservation.model.Time;

public record TimeResponse(
        long id,
        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt
) {
    public static TimeResponse from(Time time) {
        return new TimeResponse(time.getId(), time.getStartAt());
    }
}
