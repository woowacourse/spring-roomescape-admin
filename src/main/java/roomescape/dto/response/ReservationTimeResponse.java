package roomescape.dto.response;

import roomescape.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;

import java.time.format.DateTimeFormatter;

public record ReservationTimeResponse(
        long id,
        String startAt
) {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationTimeResponse from(final ReservationTime times) {
        String startTime = TIME_FORMATTER.format(times.startTime());
        return new ReservationTimeResponse(times.id(), startTime);
    }

    public static ReservationTimeResponse from(final ReservationTimeCreateRequest request, final long savedId) {
        String startTime = TIME_FORMATTER.format(request.startAt());
        return new ReservationTimeResponse(savedId, startTime);
    }
}
