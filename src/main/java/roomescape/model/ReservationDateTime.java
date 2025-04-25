package roomescape.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
        LocalDate date = LocalDate.parse(reservationDate.getDate());
        LocalTime time = LocalTime.parse(reservationTime.getStart_at());
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        LocalDateTime now = LocalDateTime.now();
        if (dateTime.isBefore(now)) {
            throw new DateTimeException("과거 예약은 불가능합니다.");
        }
    }
}
