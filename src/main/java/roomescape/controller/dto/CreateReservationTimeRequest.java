package roomescape.controller.dto;

import java.time.LocalTime;

public record CreateReservationTimeRequest(
        LocalTime startAt
) {
    public CreateReservationTimeRequest {
        if (startAt == null) {
            throw new IllegalArgumentException("startAt은 필수값입니다.");
        }
    }
}
