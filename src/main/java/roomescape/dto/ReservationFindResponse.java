package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;

public record ReservationFindResponse(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeFindResponse time
) {

    public static ReservationFindResponse from(Reservation reservation) {
        return new ReservationFindResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                ReservationTimeFindResponse.from(reservation.getReservationTime())
        );
    }
}
