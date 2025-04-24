package roomescape.dto.response;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        ReservationTime reservationTime
) {
    public static ReservationResponse toDto(Reservation reservation) {
        LocalDate date = reservation.getDate();
        ReservationTime reservationTime = reservation.getReservationTime();
        return new ReservationResponse(reservation.getId(), reservation.getName(), date, reservationTime);
    }
}
