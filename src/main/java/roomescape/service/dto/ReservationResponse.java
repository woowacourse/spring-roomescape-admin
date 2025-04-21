package roomescape.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.Reservation;

public record ReservationResponse(Long id, String name, LocalDate date, LocalTime time) {
    public ReservationResponse(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
