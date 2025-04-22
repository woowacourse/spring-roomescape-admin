package roomescape.dto.request;

import jakarta.validation.constraints.NotNull;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationRequest(
        @NotNull LocalDate date,
        @NotNull String name,
        @NotNull LocalTime time) {

    public Reservation toReservation() {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        return new Reservation(name, dateTime);
    }
}
