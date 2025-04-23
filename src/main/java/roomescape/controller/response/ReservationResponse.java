package roomescape.controller.response;

import java.time.LocalDate;
import roomescape.domain.Reservation;

public record ReservationResponse(Long id, String name, LocalDate date, ReservationTimeResponse time) {

    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                new ReservationTimeResponse(
                        reservation.getId(),
                        reservation.getTime().getStartAt()
                )
        );
    }
}
