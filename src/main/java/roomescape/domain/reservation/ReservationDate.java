package roomescape.domain.reservation;

import java.time.LocalDate;

public class ReservationDate {
    private final LocalDate date;

    public ReservationDate(LocalDate date) {
        validateDate(date);
        this.date = date;
    }

    private void validateDate(LocalDate date) {
        LocalDate nowDate = LocalDate.now();
        if (nowDate.isAfter(date)) {
            throw new IllegalArgumentException("예약은 현재 이후 날짜만 가능합니다.");
        }
    }

    public LocalDate getDate() {
        return date;
    }
}
