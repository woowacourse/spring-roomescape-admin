package roomescape.dto.response;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record TimeResponse(Long id, LocalTime startAt) {
    public static TimeResponse toDto(ReservationTime reservationTime) {
        return new TimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }
}
