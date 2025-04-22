package roomescape.dto.response;

import roomescape.infra.entity.ReservationTimeEntity;

import java.time.format.DateTimeFormatter;

public record ReservationTimeResponse(
        long id,
        String startAt
) {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationTimeResponse from(final ReservationTimeEntity timeEntity) {
        String startTime = TIME_FORMATTER.format(timeEntity.getStartAt());
        return new ReservationTimeResponse(timeEntity.getId(), startTime);
    }
}
