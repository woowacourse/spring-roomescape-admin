package roomescape.reservationTime.dto;

import java.time.LocalTime;
import roomescape.reservationTime.ReservationTime;

public record ReservationTimeDto(String startAt) {
    public ReservationTime createTime() {
        return new ReservationTime(LocalTime.parse(startAt));
    }
}
