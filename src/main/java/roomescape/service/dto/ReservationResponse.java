package roomescape.service.dto;

import java.time.LocalDate;
import roomescape.Reservation;
import roomescape.ReservationTime;

public record ReservationResponse(Long id, String name, LocalDate date, ReservationTime time) {
    public ReservationResponse(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
