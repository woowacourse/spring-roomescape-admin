package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.model.ReservationTime;

public record ReservationTimeResponse(Long id, String startAt) {

    private static final String DATE_TIME_PATTERN = "HH:mm";

    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
            reservationTime.getId(),
            reservationTime.getStartAt().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN))
        );
    }
}
