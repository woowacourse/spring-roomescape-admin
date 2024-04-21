package roomescape.admin.reservation.time;

import java.time.LocalTime;

public record TimeResponse(Long id, LocalTime startAt) {
    public static TimeResponse from(Time time) {
        return new TimeResponse(
                time.getId(), time.getStartAt()
        );
    }
}
