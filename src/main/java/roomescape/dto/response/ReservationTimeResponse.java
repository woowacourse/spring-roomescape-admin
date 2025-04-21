package roomescape.dto.response;

import roomescape.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;

import java.time.format.DateTimeFormatter;

public record ReservationTimeResponse(
        long id,
        String startAt
) {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationTimeResponse from(final ReservationTime time) {
        String startTime = TIME_FORMATTER.format(time.startTime());
        return new ReservationTimeResponse(time.id(), startTime);
    }

    public static ReservationTimeResponse from(final ReservationTimeCreateRequest request, final long savedId) {
        String startTime = TIME_FORMATTER.format(request.startAt());
        return new ReservationTimeResponse(savedId, startTime);
    }
}
