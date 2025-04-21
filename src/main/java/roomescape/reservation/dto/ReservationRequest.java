package roomescape.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservation.domain.Reservation;

public record ReservationRequest(
        @NotBlank String name,
        @NotNull LocalDate date,
        @NotNull LocalTime time
) {
    public Reservation toReservation() {
        return new Reservation(null, name, date, time);
    }
}
