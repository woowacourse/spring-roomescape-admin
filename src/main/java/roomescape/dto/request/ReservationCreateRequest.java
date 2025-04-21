package roomescape.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(String name, LocalDate date, LocalTime time) {

    public ReservationCreateRequest {
        validateBlank(name, date, time);
    }

    private void validateBlank(final String name, final LocalDate date, final LocalTime time) {
        if (name == null || name.isBlank() || date == null || time == null) {
            throw new IllegalArgumentException("빈 값으로 예약할 수 없습니다.");
        }
    }
}
