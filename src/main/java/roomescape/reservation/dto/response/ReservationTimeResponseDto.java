package roomescape.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public record ReservationTimeResponseDto(
        long id,
        @JsonFormat(pattern = "kk:mm")
        LocalTime startAt) {
}
