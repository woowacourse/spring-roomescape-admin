package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

public record ReservationResponse(Long id, String name, LocalDate date, Time time) {

    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
                reservation.getTime());
    }
}
