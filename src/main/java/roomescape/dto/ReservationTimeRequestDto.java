package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReservationTimeRequestDto(@JsonProperty(value = "start_at") String start_at) {
}
