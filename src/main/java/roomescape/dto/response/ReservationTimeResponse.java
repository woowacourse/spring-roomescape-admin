package roomescape.dto.response;

import roomescape.business.domain.ReservationTime;

import java.time.format.DateTimeFormatter;

public record ReservationTimeResponse(
        long id,
        String startAt
) {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationTimeResponse from(final ReservationTime reservationTime, final long id) {
        String startTime = TIME_FORMATTER.format(reservationTime.startTime());
        return new ReservationTimeResponse(id, startTime);
    }
}
