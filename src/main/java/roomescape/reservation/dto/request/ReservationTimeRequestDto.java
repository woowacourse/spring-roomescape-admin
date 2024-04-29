package roomescape.reservation.dto.request;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.time.LocalTime;

public record ReservationTimeRequestDto(
        @DateTimeFormat(pattern = "kk:mm")
        @NonNull
        LocalTime startAt) {
}
