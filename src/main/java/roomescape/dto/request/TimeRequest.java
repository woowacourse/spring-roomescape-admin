package roomescape.dto.request;

import java.time.LocalTime;
import roomescape.model.ReservationTime;

public record TimeRequest(
        String startAt
) {
    public ReservationTime toDomain() {
        return ReservationTime.withoutId(LocalTime.parse(startAt));
    }
}
