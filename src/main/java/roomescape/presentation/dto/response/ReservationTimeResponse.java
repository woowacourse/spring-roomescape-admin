package roomescape.presentation.dto.response;

import roomescape.business.domain.ReservationTime;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public record ReservationTimeResponse(
        Long id,
        String startAt
) {
    public ReservationTimeResponse {
        Objects.requireNonNull(id, "id가 설정되지 않았습니다.");
        Objects.requireNonNull(startAt, "시작시간이 설정되지 않았습니다.");
    }

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationTimeResponse from(final ReservationTime reservationTime, final Long id) {
        String startTime = TIME_FORMATTER.format(reservationTime.startTime());
        return new ReservationTimeResponse(id, startTime);
    }
}
