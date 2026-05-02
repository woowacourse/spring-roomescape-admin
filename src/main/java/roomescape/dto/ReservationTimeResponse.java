package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(Long id, String startAt) {

    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
            reservationTime.id(),
            reservationTime.startAt().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
