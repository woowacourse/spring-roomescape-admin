package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;

public record CreateTimeSlotRequest(
    @JsonProperty("startAt") LocalTime startAt
) {

}
