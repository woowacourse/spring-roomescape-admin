package roomescape.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record ReservationTimeResponse(long id, String startAt) {
    private static final DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationTimeResponse from(long id, LocalTime time) {
        return new ReservationTimeResponse(id, time.format(DEFAULT_TIME_FORMATTER));
    }
}
