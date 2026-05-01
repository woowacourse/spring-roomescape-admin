package roomescape.domain.reservation.dto;

import java.time.LocalDate;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.support.RoomescapeErrorCode;
import roomescape.support.RoomescapeException;

public record CreateReservationRequest(
    String name,
    LocalDate date,
    Long timeId
) {

    public void validate() {
        if (name == null || name.isBlank()) {
            throw new RoomescapeException(RoomescapeErrorCode.INVALID_RESERVATION_NAME);
        }
        if (date == null) {
            throw new RoomescapeException(RoomescapeErrorCode.INVALID_RESERVATION_DATE);
        }
        if (timeId == null) {
            throw new RoomescapeException(RoomescapeErrorCode.INVALID_RESERVATION_TIME);
        }
    }

    public Reservation toEntity(ReservationTime reservationTime) {
        return Reservation.createWithoutId(
            name,
            date,
            reservationTime
        );
    }
}
