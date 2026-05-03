package roomescape.domain.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.Reservation;

public record CreateReservationResponse(
    Long id,
    String name,
    LocalDate date,
    LocalTime time
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
