package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.entity.ReservationTime;

public record ReservationTimeResponseDto(
        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt
) {

    public static ReservationTimeResponseDto toDto(final ReservationTime reservation) {
        return new ReservationTimeResponseDto(reservation.startAt());
    }
}
