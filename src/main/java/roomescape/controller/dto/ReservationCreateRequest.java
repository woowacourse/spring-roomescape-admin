package roomescape.controller.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.util.CustomDateTimeFormatter;

public record ReservationCreateRequest(String date, String name, long timeId) {
    public Reservation toReservationWith(ReservationTime reservationTime) {
        return new Reservation(
                null,
                name,
                CustomDateTimeFormatter.getLocalDate(date),
                reservationTime
        );
    }
}
