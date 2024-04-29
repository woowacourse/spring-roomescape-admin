package roomescape.general.dto;

import java.time.LocalTime;
import java.util.Objects;
import roomescape.general.domain.ReservationTime;

public record ReservationTimeResponse(Long id, LocalTime startAt) {

    public ReservationTimeResponse {
        Objects.requireNonNull(id);
        Objects.requireNonNull(startAt);
    }

    public static ReservationTimeResponse from(final ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }
}
