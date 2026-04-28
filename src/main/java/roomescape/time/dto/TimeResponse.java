package roomescape.time.dto;

import java.time.LocalTime;
import roomescape.time.ReservationTime;

public record TimeResponse(Long id, LocalTime startAt) {
    public static TimeResponse from(ReservationTime time) {
        return new TimeResponse(time.getId(), time.getStartAt());
    }
}