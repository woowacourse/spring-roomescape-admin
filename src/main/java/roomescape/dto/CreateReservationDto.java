package roomescape.dto;

import jakarta.validation.constraints.NotNull;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.ReservationDateTime;
import roomescape.domain.ReservationInfo;

public record CreateReservationDto(@NotNull String name, @NotNull LocalDate date, @NotNull LocalTime time) {

    public ReservationInfo toReservationInfo(final Clock clock) {
        return new ReservationInfo(name, new ReservationDateTime(date, time, clock));
    }
}
