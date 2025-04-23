package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.entity.ReservationTime;

public record ReservationTimeResponse(
        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt
) {

    public static ReservationTimeResponse toDto(final ReservationTime reservation) {
        return new ReservationTimeResponse(reservation.startAt());
    }
}
