package roomescape.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.domain.Time;

public record TimeSaveResponse(
        Long id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime startAt) {
    public static TimeSaveResponse from(Time time) {
        return new TimeSaveResponse(
                time.getId(),
                time.getStartAt()
        );
    }
}
