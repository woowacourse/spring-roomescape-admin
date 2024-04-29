package roomescape.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeRequest(
        @JsonFormat(pattern = "HH:mm") LocalTime startAt) {

    public ReservationTime toDomain() {
        return new ReservationTime(startAt());
    }

}
