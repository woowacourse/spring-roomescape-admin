package roomescape.domain;

import java.time.LocalDate;

public class ReservationDate {

    private final LocalDate date;

    public ReservationDate(final LocalDate date) {
        validateDate(date);
        this.date = date;
    }

    private void validateDate(final LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("예약 날짜는 null이 될 수 없습니다.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("과거 날짜로 예약할 수 없습니다.");
        }
    }

    public LocalDate getDate() {
        return date;
    }
}
