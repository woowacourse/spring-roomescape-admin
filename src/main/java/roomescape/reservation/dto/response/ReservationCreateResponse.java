package roomescape.reservation.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservation.domain.Reservation;

public record ReservationCreateResponse(
        long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public static ReservationCreateResponse of(long id, Reservation reservation) {
        return new ReservationCreateResponse(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
