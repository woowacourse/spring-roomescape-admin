package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(Long id, String name, LocalDate date, LocalTime time) {
    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getClientName().getValue(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
