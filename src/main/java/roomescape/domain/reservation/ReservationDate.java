package roomescape.domain.reservation;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ReservationDate {
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private final LocalDate date;

    public ReservationDate(LocalDate date) {
        validateDate(date);
        this.date = date;
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        }

        if (LocalDate.now().isAfter(date)) {
            throw new IllegalArgumentException("예약은 현재 이후 날짜만 가능합니다.");
        }
    }

    public LocalDate getDate() {
        return date;
    }
}
