package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationTime {
    private static final int RESERVATION_DURATION_HOUR = 1;

    private final LocalDateTime reservationStartDateTime;

    public ReservationTime(LocalDateTime reservationStartDateTime) {
        this.reservationStartDateTime = reservationStartDateTime;
    }

    public boolean isConflictWith(ReservationTime other) {
        return !(endTime().isBefore(other.reservationStartDateTime) || reservationStartDateTime.isAfter(
                other.endTime()));
    }

    private LocalDateTime endTime() {
        return reservationStartDateTime.plusHours(RESERVATION_DURATION_HOUR);
    }

    public LocalDate getStartDate() {
        return reservationStartDateTime.toLocalDate();
    }

    public LocalTime getStartTime() {
        return reservationStartDateTime.toLocalTime();
    }
}
