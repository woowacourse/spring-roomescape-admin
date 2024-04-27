package roomescape.dto.reservation;

import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.Time;

import java.time.LocalDate;

public record ReservationResponse(Long id, String name, LocalDate date, Time time) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(),
                reservation.getDate(), reservation.getTime());
    }
}
