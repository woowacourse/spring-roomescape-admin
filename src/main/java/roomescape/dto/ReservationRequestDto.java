package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.model.Reservation;

public record ReservationRequestDto(
        String name,
        LocalDate date,
        LocalTime time
) {

    public Reservation toReservation() {
        return new Reservation(name, date, time);
    }
}
