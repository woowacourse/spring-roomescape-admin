package roomescape.dto.response;

import roomescape.business.domain.ReservationTime;

import java.time.format.DateTimeFormatter;
import java.util.List;

public record ReservationTimeResponse(
        long id,
        String startAt
) {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationTimeResponse from(final ReservationTime time) {
        String startTime = TIME_FORMATTER.format(time.startTime());
        return new ReservationTimeResponse(time.id(), startTime);
    }

    public static List<ReservationTimeResponse> fromList(final List<ReservationTime> reservationTimes) {
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }
}
