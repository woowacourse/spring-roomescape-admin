package roomescape.entity;

import java.time.LocalDateTime;

public class ReservationTime {
    private static final int RESERVATION_DURATION_HOUR = 1;

    private final LocalDateTime startTime;

    public ReservationTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public boolean isConflictWith(ReservationTime other) {
        return !(endTime().isBefore(other.startTime) || startTime.isAfter(other.endTime()));
    }

    private LocalDateTime endTime() {
        return startTime.plusHours(RESERVATION_DURATION_HOUR);
    }
}
