package roomescape.reservation.ui.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationRequestDto(@NotBlank String name,
                                    @NotNull LocalDate date,
                                    @NotNull LocalTime time) {

    public Reservation toDomain() {
        return Reservation.createWithoutId(name, LocalDateTime.of(date, time));
    }
}
