package roomescape.model;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class ReservationDateTime {
    private final ReservationDate date;
    private final ReservationTime time;

    public ReservationDateTime(ReservationDate date, ReservationTime time) {
        validateDateTime(date, time);
        this.date = date;
        this.time = time;
    }

    public ReservationDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    private void validateDateTime(ReservationDate reservationDate, ReservationTime reservationTime) {

        LocalDateTime dateTime = LocalDateTime.of(reservationDate.getDate(), reservationTime.getStartAt());
        LocalDateTime now = LocalDateTime.now();
        if (dateTime.isBefore(now)) {
            throw new DateTimeException("과거 예약은 불가능합니다.");
        }
    }
}
