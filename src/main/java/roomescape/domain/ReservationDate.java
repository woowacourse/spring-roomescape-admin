package roomescape.domain;

import java.time.LocalDate;
import java.util.Objects;

public class ReservationDate {
    private LocalDate date;

    private ReservationDate(String date) {
        this(LocalDate.parse(date));
    }

    public ReservationDate(LocalDate date) {
        validateDate(date);
        this.date = date;
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("예약 일자는 필수 입력값 입니다.");
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
        if (!(o instanceof ReservationDate that)) {
            return false;
        }
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }
}
