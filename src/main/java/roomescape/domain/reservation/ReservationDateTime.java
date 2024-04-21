package roomescape.domain.reservation;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class ReservationDateTime {
    private static final int IN_ADVANCE_RESERVATION_DAYS = 1;

    private final LocalDateTime dateTime;

    public ReservationDateTime(LocalDate date, LocalTime time, Clock clock) {
        validateEmptyDateTime(date, time);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        validateAfterBaseDateTime(dateTime, clock);
        this.dateTime = dateTime;
    }

    private void validateEmptyDateTime(LocalDate date, LocalTime time) {
        if (date == null || time == null) {
            throw new IllegalArgumentException("예약 날짜, 시간이 비어있습니다.");
        }
    }

    private void validateAfterBaseDateTime(LocalDateTime dateTime, Clock clock) { // todo 비교 로직 service로 이동하고 clock 제거
        LocalDateTime baseDateTIme = LocalDateTime.now(clock).plusDays(IN_ADVANCE_RESERVATION_DAYS);
        if (dateTime.isBefore(baseDateTIme)) {
            throw new IllegalArgumentException(
                    String.format("최소 %d일 전 예약해야 합니다.", IN_ADVANCE_RESERVATION_DAYS)
            );
        }
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    protected LocalDate toLocalDate() {
        return dateTime.toLocalDate();
    }

    protected LocalTime toLocalTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReservationDateTime that = (ReservationDateTime) o;
        return Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime);
    }
}
