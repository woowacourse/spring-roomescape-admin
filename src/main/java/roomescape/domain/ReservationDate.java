package roomescape.domain;

import java.time.LocalDate;
import roomescape.domain.exception.EmptyReservationDateException;

public class ReservationDate {

    private final LocalDate date;

    public ReservationDate(final LocalDate date) {
        validateDate(date);
        this.date = date;
    }

    private void validateDate(final LocalDate date) {
        if (date == null) {
            throw new EmptyReservationDateException("예약 날짜는 null이 될 수 없습니다.");
        }
    }

    public LocalDate getDate() {
        return date;
    }
}
