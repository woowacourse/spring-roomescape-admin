package roomescape.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.domain.Time;

public record TimeResponse(
        Long id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime startAt) {
    public static TimeResponse from(Time time) {
        return new TimeResponse(
                time.getId(),
                time.getStartAt()
        );
    }
}
