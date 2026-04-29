package roomescape.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public record ReservationTimeRequestDto(
        @JsonProperty("start_at")
        LocalTime startAt
) {
}
