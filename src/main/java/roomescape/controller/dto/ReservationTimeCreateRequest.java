package roomescape.controller.dto;

import roomescape.domain.ReservationTime;
import roomescape.util.CustomDateTimeFormatter;

public record ReservationTimeCreateRequest(String startAt) {
    public ReservationTime toReservationTime() {
        return new ReservationTime(
                null,
                CustomDateTimeFormatter.getLocalTime(startAt)
        );
    }
}
