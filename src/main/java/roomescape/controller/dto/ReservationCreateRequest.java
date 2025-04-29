package roomescape.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(@NotBlank String name, @NotNull LocalDate date, @NotNull Long timeId) {

    public Reservation toReservation() {
        return new Reservation(null, name, date, new ReservationTime(timeId, LocalTime.MIN));
    }
}
