package roomescape.reservationtime.dto;

import java.time.LocalTime;
import roomescape.reservationtime.domain.ReservationTime;

public record CreateReservationTimeRequest(
        LocalTime startAt
) {
    public void validate() {
        if (startAt == null) {
            throw new IllegalArgumentException("시간을 입력하지 않으셨습니다.");
        }
    }

    public ReservationTime toReservationTime() {
        return ReservationTime.createWithoutId(startAt);
    }
}
