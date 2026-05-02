package roomescape.dto;

import static java.util.Objects.requireNonNull;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeResponse(long id, LocalTime startAt) {

    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        requireNonNull(reservationTime, "변환할 예약 시간 엔티티가 null입니다.");
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }
}
