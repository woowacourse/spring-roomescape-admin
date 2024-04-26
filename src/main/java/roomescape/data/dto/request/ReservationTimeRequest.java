package roomescape.data.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalTime;

public class ReservationTimeRequest {
    private final LocalTime startAt;

    @JsonCreator
    public ReservationTimeRequest(final LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
