package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.Time;

public record TimeResponse(long id, String startAt) {
    private static final String TIME_FORMAT = "HH:mm";

    public TimeResponse(Time time) {
        this(time.getId(), time.getStartAt().format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
    }
}
