package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.model.ReservationTime;

public record ReservationTimeResponse(Long id, String startAt) {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationTimeResponse from(final ReservationTime reservationTime) {
        final String startAt = reservationTime.getStartAt().format(TIME_FORMATTER);
        return new ReservationTimeResponse(reservationTime.getId(), startAt);
    }
}
