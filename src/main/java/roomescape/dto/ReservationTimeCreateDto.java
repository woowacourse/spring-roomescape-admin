package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeCreateDto(String startAt) {
    public ReservationTime toDomain() {
        LocalTime time = LocalTime.parse(startAt);
        return new ReservationTime(time);
    }
}
