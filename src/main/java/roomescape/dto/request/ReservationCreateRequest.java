package roomescape.dto.request;

import java.time.LocalDate;

public record ReservationCreateRequest(String name, LocalDate date, Long timeId) {

    public ReservationCreateRequest {
        validateBlank(name, date, timeId);
    }

    private void validateBlank(final String name, final LocalDate date, final Long timeId) {
        if (name == null || name.isBlank() || date == null || timeId == null) {
            throw new IllegalArgumentException("빈 값으로 예약할 수 없습니다.");
        }
    }
}
