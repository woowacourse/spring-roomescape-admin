package roomescape.domain.time;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;

public class ReservationTimeRequestDTO {
    private final LocalTime startAt;

    @JsonCreator
    public ReservationTimeRequestDTO(@JsonProperty("startAt") LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return this.startAt;
    }
}
