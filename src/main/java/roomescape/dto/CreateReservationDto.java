package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.ReservationDateTime;
import roomescape.domain.ReservationInfo;

public record CreateReservationDto(
        @NotNull @NotBlank
        String name,

        @NotNull
        LocalDate date,

        @NotNull
        LocalTime time
) {

    public ReservationInfo toReservationInfo(final Clock clock) {
        return new ReservationInfo(name, ReservationDateTime.createNewReservationTime(date, time, clock));
    }
}
