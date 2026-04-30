package roomescape.domain.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

public record ReservationTimeRequest(
        @NotNull(message = "시간은 필수 값입니다.")
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime startAt
) {
}
