package roomescape.controller.dto;

import roomescape.domain.ReservationTime;
import roomescape.util.CustomDateTimeFormatter;

public record ReservationTimeCreateResponse(long id, String startAt) {
    public static ReservationTimeCreateResponse of(ReservationTime createdReservationTime) {
        return new ReservationTimeCreateResponse(
                createdReservationTime.getId(),
                CustomDateTimeFormatter.getFormattedTime(createdReservationTime.getStartAt())
        );
    }
}
