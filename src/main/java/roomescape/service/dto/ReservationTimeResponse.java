package roomescape.service.dto;

import java.time.LocalTime;
import roomescape.ReservationTime;

public record ReservationTimeResponse(Long id, LocalTime startAt) {
    public ReservationTimeResponse(final ReservationTime reservationTime) {
        this(
                reservationTime.getId(),
                reservationTime.getStartAt()
        );
    }
}
