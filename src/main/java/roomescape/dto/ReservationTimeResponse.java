package roomescape.dto;

import roomescape.domain.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeResponse(
        long id,
        LocalTime startAt
) {
    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
                reservationTime.getId(),
                reservationTime.getStartAt());
    }
}
