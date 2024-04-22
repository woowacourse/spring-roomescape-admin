package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeRequestDto {
    private final String startAt;

    @JsonCreator
    public TimeRequestDto(@JsonProperty("startAt") final String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }
}
