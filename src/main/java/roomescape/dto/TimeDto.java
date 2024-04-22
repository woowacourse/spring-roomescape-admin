package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.time.Time;

public record TimeDto(Long id, String startAt) {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static TimeDto from(Time time) {
        return new TimeDto(time.getId(), time.getStartAt().format(DATE_TIME_FORMATTER));
    }
}
