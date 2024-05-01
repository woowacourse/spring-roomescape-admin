package roomescape.console.controller.response;

import roomescape.core.service.response.ReservationResponseDto;

import java.time.LocalDate;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        ReservationTimeResponse time
) {
    public static ReservationResponse from(ReservationResponseDto reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                new ReservationTimeResponse(reservation.time().id(), reservation.time().startAt()));
    }
}
