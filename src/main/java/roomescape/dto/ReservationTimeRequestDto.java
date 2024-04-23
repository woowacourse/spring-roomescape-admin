package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationTimeRequestDto {
    private final String startAt;

    @JsonCreator
    public ReservationTimeRequestDto(@JsonProperty("startAt") final String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }
}
