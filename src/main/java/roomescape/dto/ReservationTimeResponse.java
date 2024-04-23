package roomescape.dto;

import java.time.format.DateTimeFormatter;
import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(Long id, String startAt) {
    public static ReservationTimeResponse toResponse(ReservationTime reservationTime){
        return new ReservationTimeResponse(
                reservationTime.getId(),
                reservationTime.getTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
