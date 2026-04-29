package roomescape.domain;

import java.time.LocalDateTime;

public record ReservationTime(LocalDateTime startTime, LocalDateTime endTime) {

    public boolean isOverlapping(ReservationTime otherReservationTime) {
        return startTime.isBefore(otherReservationTime.endTime) && otherReservationTime.startTime.isBefore(endTime);
    }
}
