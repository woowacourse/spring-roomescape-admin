package roomescape.controller.dto;

import roomescape.domain.ReservationTime;
import roomescape.util.CustomDateTimeFormatter;

public record ReservationTimeCreateRequest(String startAt) {
    public ReservationTime to() {
        return new ReservationTime(
                null,
                CustomDateTimeFormatter.getLocalTime(startAt)
        );
    }
}
