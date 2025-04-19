package roomescape.reservation.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

public final class ReservationDateTime {

    public static final LocalTime START_RESERVATION_TIME = LocalTime.of(10, 0);
    public static final LocalTime LAST_RESERVATION_TIME = LocalTime.of(23, 0);

    private final LocalDateTime dateTime;

    public ReservationDateTime(final LocalDateTime dateTime) {
        validate(dateTime);
        this.dateTime = dateTime;
    }

    public static ReservationDateTime from(final LocalDateTime dateTime) {
        return new ReservationDateTime(dateTime);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    private void validate(final LocalDateTime dateTime) {
        validateTime(dateTime.toLocalTime());
    }

    private void validateTime(final LocalTime time) {
        if (time.isBefore(START_RESERVATION_TIME) || time.isAfter(LAST_RESERVATION_TIME)) {
            throw new IllegalArgumentException("예약할 수 없는 시간입니다.");
        }
    }
}
