package roomescape.reservation.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

public record ReservationTimeRequestDto(
        @DateTimeFormat(pattern = "kk:mm")
        LocalTime startAt) {
}
