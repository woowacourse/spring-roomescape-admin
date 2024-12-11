package roomescape.service.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import roomescape.domain.Time;

public record TimeResponse(long id, String startAt) {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public TimeResponse(long id, LocalTime time) {
        this(id, time.format(FORMATTER));
    }

    public TimeResponse(Time time) {
        this(time.getId(), time.getStartAt().format(FORMATTER));
    }

}
