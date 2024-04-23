package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationTime {
    public static final int RESERVATION_DURATION_HOUR = 1;

    private final LocalDateTime start;

    public ReservationTime(LocalDateTime start) {
        validateNonNull(start);
        this.start = start;
    }

    private void validateNonNull(LocalDateTime startTime) {
        if (startTime == null) {
            throw new NullPointerException("예약 시간은 Null이 될 수 없습니다");
        }
    }

    public LocalDateTime getStartDateTime() {
        return start;
    }

    public LocalDateTime getEndDateTime() {
        return start.plusHours(RESERVATION_DURATION_HOUR);
    }

    public LocalDate getStartDate() {
        return start.toLocalDate();
    }

    public LocalTime getStart() {
        return start.toLocalTime();
    }
}
