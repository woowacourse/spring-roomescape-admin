package roomescape.controller.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationDto(
        long id,
        String name,
        LocalDate date,
        ReservationTime time
) {
    public ReservationDto(final Reservation reservation) {
        this(reservation.getId(), reservation.getName(), reservation.getDate(),
                reservation.getReservationTime());
    }
}
