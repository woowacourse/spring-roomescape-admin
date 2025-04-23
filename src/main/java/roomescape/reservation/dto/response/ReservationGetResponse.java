package roomescape.reservation.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservation.domain.Reservation;

public record ReservationGetResponse(
        long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public static ReservationGetResponse from(long id, Reservation reservation) {
        return new ReservationGetResponse(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}