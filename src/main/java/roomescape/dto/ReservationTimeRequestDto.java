package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReservationTimeRequestDto(@JsonProperty(value = "startAt") String startAt) {
}
