package roomescape.dto.reservationtime;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record ReservationTimeResponse(long id, String startAt) {
    private static final DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public ReservationTimeResponse(long id, LocalTime time) {
        this(id, time.format(DEFAULT_TIME_FORMATTER));
    }
}
