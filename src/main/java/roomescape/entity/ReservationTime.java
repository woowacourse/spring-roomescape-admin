package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationTime {
    private static final int RESERVATION_DURATION_HOUR = 1;

    private final LocalDateTime startTime;

    public ReservationTime(LocalDateTime startTime) {
        validateNonNull(startTime);
        this.startTime = startTime;
    }

    private void validateNonNull(LocalDateTime startTime) {
        if (startTime == null) {
            throw new NullPointerException("예약 시간은 Null이 될 수 없습니다");
        }
    }

    public boolean isConflictWith(ReservationTime other) {
        boolean noConflict = endTime().isBefore(other.startTime) || startTime.isAfter(other.endTime());
        return !noConflict;
    }

    private LocalDateTime endTime() {
        return startTime.plusHours(RESERVATION_DURATION_HOUR);
    }

    public LocalDate getStartDate() {
        return startTime.toLocalDate();
    }

    public LocalTime getStartTime() {
        return startTime.toLocalTime();
    }
}
