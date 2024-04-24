package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeRequest(
        String time
) {

    public ReservationTime toEntity() {
        return new ReservationTime(LocalTime.parse(time));
    }
}
