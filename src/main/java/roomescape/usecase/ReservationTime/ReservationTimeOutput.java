package roomescape.usecase.ReservationTime;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeOutput(Long id, LocalTime startAt) {
    public ReservationTimeOutput(ReservationTime time) {
        this(time.getId(), time.getStart_at());
    }
}
