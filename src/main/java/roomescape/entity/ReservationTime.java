package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationTime {
    private static final int RESERVATION_DURATION_HOUR = 1;

    private final LocalDateTime startDateTime;

    public ReservationTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public boolean isConflictWith(ReservationTime other) {
        return !(endTime().isBefore(other.startDateTime) || startDateTime.isAfter(other.endTime()));
    }

    private LocalDateTime endTime() {
        return startDateTime.plusHours(RESERVATION_DURATION_HOUR);
    }

    public LocalDate getStartDate() {
        return startDateTime.toLocalDate();
    }

    public LocalTime getStartTime() {
        return startDateTime.toLocalTime();
    }
}
