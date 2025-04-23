package roomescape.dto;

import java.time.LocalTime;
import roomescape.entity.ReservationTime;

public record ReservationTimeResponse(long id, LocalTime time){

    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
                reservationTime.getId(),
                reservationTime.getTime()
        );
    }
}
