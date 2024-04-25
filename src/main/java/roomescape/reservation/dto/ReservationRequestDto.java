package roomescape.reservation.dto;

import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.ReservationTime;

import java.time.LocalTime;

public record ReservationRequestDto(String name, String date, long timeId) {
    private static final long UNIDENTIFIED_ID = 0;
    private static final LocalTime UNIDENTIFIED_TIME = LocalTime.now();

    public Reservation toReservation() {
        return new Reservation(UNIDENTIFIED_ID, name, date, new ReservationTime(timeId, UNIDENTIFIED_TIME));
    }
}
