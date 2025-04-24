package roomescape.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record TimeResponse(
        Long id,
        @JsonFormat(pattern = "HH:mm") LocalTime startAt) {
    public static TimeResponse toDto(ReservationTime reservationTime) {
        return new TimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }
}
