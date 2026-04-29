package roomescape.reservationtime.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.reservationtime.domain.ReservationTime;

public record ReservationTimeResponse(
        long id,

        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt
) {
    public ReservationTimeResponse(ReservationTime reservationTime) {
        this(
                reservationTime.getId(),
                reservationTime.getStartAt());
    }
}
