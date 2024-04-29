package roomescape.dto.reservationtime;

import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(
        Long id,
        String startAt
) {

    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        return new ReservationTimeResponse(
                reservationTime.getId(),
                reservationTime.getStartAt().toString());
    }
}
