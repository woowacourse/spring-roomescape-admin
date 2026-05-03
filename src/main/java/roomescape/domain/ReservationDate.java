package roomescape.domain;

import java.time.LocalDate;

public record ReservationDate(LocalDate value) {
    public ReservationDate {
        if (value == null) {
            throw new IllegalArgumentException("[ERROR] 날짜는 null일 수 없습니다.");
        }
    }
}
