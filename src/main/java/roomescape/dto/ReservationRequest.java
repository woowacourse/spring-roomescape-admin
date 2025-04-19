package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.entity.Reservation;

public record ReservationRequest(
        @NotBlank String name,
        @NotNull LocalDate date,
        @NotNull LocalTime time
) {
    public Reservation toReservation(Long id) {
        return new Reservation(id, name, date, time);
    }
}
