package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;

public record ReservationTimeAddRequest(@JsonProperty("startAt") LocalTime startTime) {
}
