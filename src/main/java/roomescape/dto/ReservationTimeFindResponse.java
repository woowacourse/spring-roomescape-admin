package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeFindResponse(
        Long id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        LocalTime startAt) {

    public static ReservationTimeFindResponse from(ReservationTime reservationTime) {
        return new ReservationTimeFindResponse(reservationTime.getId(), reservationTime.getStartAt());
    }
}
