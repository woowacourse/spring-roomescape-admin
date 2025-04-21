package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.ReservationTime;

public record ReservationTimeDto(
        Long id, String startAt
) {

    public static ReservationTimeDto from(ReservationTime reservationTime) {
        return new ReservationTimeDto(
                reservationTime.getId(),
                reservationTime.getStartAt().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
