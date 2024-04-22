package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.time.ReservationTime;

public record ReservationTimeDto(Long id, String startAt) {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static ReservationTimeDto from(ReservationTime reservationTime) {
        return new ReservationTimeDto(reservationTime.getId(),
                reservationTime.getStartAt().format(DATE_TIME_FORMATTER));
    }
}
