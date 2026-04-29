package roomescape.controller.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeDto(
        Long id,
        LocalTime startAt
) {

    public static ReservationTimeDto from(ReservationTime reservationTime) {
        return new ReservationTimeDto(reservationTime.getId(), reservationTime.getStartAt());
    }

    public ReservationTime toReservationTime() {
        return new ReservationTime(this.id, this.startAt);
    }
}
