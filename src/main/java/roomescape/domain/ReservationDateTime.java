package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import roomescape.domain.exception.PastReservationException;

public class ReservationDateTime {

    private final ReservationDate date;
    private final ReservationTime time;

    public ReservationDateTime(final ReservationDate date, final ReservationTime time) {
        validateDateTime(date, time);
        this.date = date;
        this.time = time;
    }

    private void validateDateTime(final ReservationDate date, final ReservationTime time) {
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        LocalDateTime now = ZonedDateTime.now(zoneId).toLocalDateTime();

        if (getDateTime(date, time).isBefore(now)) {
            throw new PastReservationException("과거 날짜로 예약할 수 없습니다.");
        }
    }

    private LocalDateTime getDateTime(final ReservationDate date, final ReservationTime time) {
        return LocalDateTime.of(date.getDate(), time.getStartAt());
    }

    public LocalDate getDate() {
        return date.getDate();
    }

    public LocalTime getTime() {
        return time.getStartAt();
    }

    public Long getTimeId() {
        return time.getId();
    }
}
