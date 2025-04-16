package roomescape;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        @NotBlank String name,
        @NotNull LocalDate date,
        @NotNull LocalTime time
) {
    public Reservation toEntity() {
        return new Reservation(null, name, date, time);
    }
}
