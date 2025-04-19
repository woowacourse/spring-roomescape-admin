package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDateTime;

public record CreateReservationDto(
        @NotNull @NotBlank
        String name,

        @NotNull
        LocalDate date,

        @NotNull
        LocalTime time
) {

    public Reservation toReservation(final LocalDateTime now) {
        return new Reservation(null, name, ReservationDateTime.createNewReservationTime(date, time, now));
    }
}
