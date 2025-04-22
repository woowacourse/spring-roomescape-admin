package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public record CreateReservationRequest(
    @JsonProperty("name") String name,
    @JsonProperty("date") LocalDate date,
    @JsonProperty("timeId") Long timeSlotId
) {

}
