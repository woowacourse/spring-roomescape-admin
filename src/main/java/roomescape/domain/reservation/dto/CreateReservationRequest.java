package roomescape.domain.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.Reservation;

public record CreateReservationRequest(
    Long id,
    String name,
    LocalDate date,
    LocalTime time
) {


    public Reservation toEntity() {
        return Reservation.createWithoutId(
            name,
            date,
            time
        );
    }
}
