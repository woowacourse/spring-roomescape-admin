package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public record ReservationTimeResDto(
        Long id,
        @JsonFormat(pattern = "HH:mm") LocalTime startAt
) {
}
