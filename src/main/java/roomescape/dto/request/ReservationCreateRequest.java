package roomescape.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ReservationCreateRequest(
        @NotBlank String name,
        @NotNull LocalDate date,
        @NotNull LocalTime time
) {
    public Reservation dtoToReservation() {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        return new Reservation(name, dateTime);
    }
}
