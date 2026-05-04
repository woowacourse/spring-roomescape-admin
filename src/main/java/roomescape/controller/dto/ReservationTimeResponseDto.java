package roomescape.controller.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeResponseDto(
        Long id,
        LocalTime startAt
) {

    public static ReservationTimeResponseDto from(ReservationTime reservationTime) {
        return new ReservationTimeResponseDto(reservationTime.getId(), reservationTime.getStartAt());
    }

    public ReservationTime toReservationTime() {
        return new ReservationTime(this.id, this.startAt);
    }
}
