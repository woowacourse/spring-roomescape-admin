package roomescape.controller.dto;

import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

public record ReservationTimeRegister(
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime startAt
) {
}
