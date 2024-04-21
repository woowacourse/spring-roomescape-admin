package roomescape.controller;

import roomescape.domain.ReservationTime;
import roomescape.util.CustomDateTimeFormatter;

public record CreateReservationTimeResponse(Long id, String startAt) {
    public static CreateReservationTimeResponse of(ReservationTime createdReservationTime) {
        return new CreateReservationTimeResponse(
                createdReservationTime.getId(),
                CustomDateTimeFormatter.getFormattedTime(createdReservationTime.getStartAt())
        );
    }
}
