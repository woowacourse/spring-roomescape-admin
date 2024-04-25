package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeAddRequest(
        @JsonProperty("startAt") @JsonFormat(pattern = "HH:mm") LocalTime startTime
) {
    public ReservationTime toEntity() {
        return new ReservationTime(startTime);
    }
}
