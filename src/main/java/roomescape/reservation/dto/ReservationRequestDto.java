package roomescape.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservation.entity.Reservation;

public record ReservationRequestDto(
        String name,
        LocalDate date,
        LocalTime time
) {
    public Reservation toEntity(){

        return new Reservation(
                null,
                name,
                date,
                time
        );
    }
}
