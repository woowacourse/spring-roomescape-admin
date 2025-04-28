package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.entity.ReservationTime;

import java.time.LocalTime;

public record ReservationTimeRequest(
        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt
) {

    public ReservationTimeRequest {
        validateStartAt(startAt);
    }

    private void validateStartAt(final LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException("[ERROR] 예약시간은 반드시 입력해야 합니다.");
        }
    }

    public ReservationTime toEntity() {
        return ReservationTime.of(startAt);
    }
}
