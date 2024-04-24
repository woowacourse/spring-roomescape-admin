package roomescape.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.domain.Time;

public record TimeFindAllResponse(
        Long id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime startAt) {
    public static TimeFindAllResponse from(Time time) {
        return new TimeFindAllResponse(
                time.getId(),
                time.getStartAt()
        );
    }
}
