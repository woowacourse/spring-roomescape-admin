package roomescape.reservation.service.dto;

import java.time.LocalDate;
import roomescape.reservation.Reservation;
import roomescape.reservationtime.service.dto.ReservationTimeResponse;

public record ReservationResponse(Long id, String name, LocalDate date, ReservationTimeResponse time) {
    public ReservationResponse(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                new ReservationTimeResponse(reservation.getTime())
        );
    }
}
