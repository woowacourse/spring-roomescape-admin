package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReservationDto(@JsonProperty(value = "name", defaultValue = "name") String name,@JsonProperty("date") String date,@JsonProperty("time") String time) {
}
