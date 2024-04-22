package roomescape.dto.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public record ReservationTimeWebResponse(
        Long id,
        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt) {
}
