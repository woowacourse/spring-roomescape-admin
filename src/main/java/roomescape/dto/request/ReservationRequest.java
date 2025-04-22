package roomescape.dto.request;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationRequest(LocalDate date, String name, LocalTime time) {

    public Reservation toReservation() {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        return new Reservation(name, dateTime);
    }
}
