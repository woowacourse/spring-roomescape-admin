package roomescape.domain.reservationtime.dto;

import roomescape.domain.reservationtime.ReservationTime;
import roomescape.support.exception.RoomescapeErrorCode;
import roomescape.support.exception.RoomescapeException;

public record CreateTimeRequest(
    String startAt
) {

    public void validate() {
        if (startAt == null || startAt.isBlank()) {
            throw new RoomescapeException(RoomescapeErrorCode.INVALID_RESERVATION_TIME);
        }
    }

    public ReservationTime toEntity() {
        return ReservationTime.createWithoutId(startAt);
    }
}
