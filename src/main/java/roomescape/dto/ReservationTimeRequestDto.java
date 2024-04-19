package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import roomescape.entity.ReservationTime;
import java.time.LocalTime;

public class ReservationTimeRequestDto {

    private final LocalTime startAt;

    public ReservationTimeRequestDto(@JsonProperty("startAt") LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTime toEntity() {
        return new ReservationTime(null, startAt);
    }
}
