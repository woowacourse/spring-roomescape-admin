package roomescape.domain.reservation.dto;

import java.time.LocalDate;
import roomescape.domain.reservation.Reservation;

public record CreateReservationResponse(
    Long id,
    String name,
    LocalDate date,
    String time
) {

    public static CreateReservationResponse from(Reservation reservation) {
        return new CreateReservationResponse(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate(),
            reservation.getTime().getStartAt()
        );
    }
}
