package roomescape.controller.time.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.model.Time;

public record TimeResponse(
        Long id,
        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt) {

    public static Time from(final Long id, final Time time) {
        return Time.of(id, time.getStartAt());
    }

}
