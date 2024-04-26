package roomescape.entity;

import java.time.LocalDate;
import java.util.Objects;

public class ReservationDate {
    private final LocalDate date;

    public ReservationDate(LocalDate date) {
        validate(date);
        this.date = date;
    }

    private void validate(LocalDate date) {
        validateNonNull(date);
        validateNonPassedDate(date);
    }

    private void validateNonNull(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("예약 날짜는 Null일 수 없습니다");
        }
    }

    private void validateNonPassedDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalStateException("예약 날짜는 현재 날짜보다 이전일 수 없습니다 예약날짜: " + date);
        }
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReservationDate that = (ReservationDate) o;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
