package roomescape.domain.reservationtime.dto;

import java.time.LocalTime;
import roomescape.domain.reservationtime.ReservationTime;

public record CreateTimeRequest(
    LocalTime startAt
) {

    public void validate() {
        if (startAt == null) {
            throw new IllegalArgumentException("시간은 필수입니다.");
        }
    }

    public ReservationTime toEntity() {
        return ReservationTime.createWithoutId(startAt);
    }
}
