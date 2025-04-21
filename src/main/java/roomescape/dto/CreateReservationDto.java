package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDateTime;
import roomescape.domain.ReservationTime;

public record CreateReservationDto(
        @NotNull @NotBlank
        String name,

        @NotNull
        LocalDate date,

        @NotNull
        Long timeId
) {

    public Reservation toReservationWith(ReservationTime reservationTime, LocalDateTime now) {
        return new Reservation(
                null,
                name,
                ReservationDateTime.createNewReservationTime(date, reservationTime, now)
        );
    }
}
