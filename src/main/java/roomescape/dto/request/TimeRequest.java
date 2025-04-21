package roomescape.dto.request;

import java.time.DateTimeException;
import java.time.LocalTime;
import roomescape.model.ReservationTime;

public record TimeRequest(
        String startAt
) {
    public ReservationTime toDomain() {
        return ReservationTime.withoutId(parseStartAt());
    }

    private LocalTime parseStartAt() {
        try {
            return LocalTime.parse(startAt);
        } catch (DateTimeException e) {
            throw new IllegalStateException("TimeRequest의 startAt이 잘못된 형식입니다.");
        }
    }
}
