package roomescape.application.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(
        Long id,
        String startAt
) {

    private static final String TIME_WITHOUT_SECONDS_FORMAT = "HH:mm";

    public ReservationTimeResponse(ReservationTime reservationTime) {
        this(
                reservationTime.getId(),
                reservationTime.formatTime(DateTimeFormatter.ofPattern(TIME_WITHOUT_SECONDS_FORMAT))
        );
    }
}
