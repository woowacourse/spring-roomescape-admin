package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;

public record ReservationTimeAddRequest(
        @JsonProperty("startAt") @JsonFormat(pattern = "HH:mm") LocalTime startTime
) {
}
