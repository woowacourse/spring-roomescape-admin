package roomescape.dto;

import roomescape.ReservationTime;

public record ReservationTimeResponse(
        Long id,
        String startAt
) {
    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }
}
