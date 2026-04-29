package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.entity.ReservationTime;

public record ReservationTimeResponse(Long id, String startAt) {

    public static ReservationTimeResponse toDto(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
            reservationTime.getId(),
            reservationTime.getStartAt().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
