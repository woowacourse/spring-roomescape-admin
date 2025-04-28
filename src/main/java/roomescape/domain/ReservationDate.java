package roomescape.domain;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class ReservationDate {
    private final LocalDate date;

    public ReservationDate(final LocalDate date) {
        this.date = Objects.requireNonNull(date, "예약 날짜는 null일 수 없습니다.");
    }

    public ReservationDate(final String date) {
        this.date = parseDate(Objects.requireNonNull(date, "예약 날짜는 null일 수 없습니다."));
    }

    public LocalDate date() {
        return date;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservationDate date1)) {
            return false;
        }
        return Objects.equals(date, date1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("예약 날짜 형식을 확인해주세요.");
        }
    }
}
