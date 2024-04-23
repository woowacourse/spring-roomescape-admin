package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.ReservationTime;

public record ReservationTimeResponseDto(Long id, String startAt) {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationTimeResponseDto from(ReservationTime time) {
        String startAt = time.getStartAt().format(TIME_FORMATTER);
        return new ReservationTimeResponseDto(time.getId(), startAt);
    }
}
