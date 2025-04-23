package roomescape.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import roomescape.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(@NotBlank String name, @NotNull LocalDate date, @NotNull LocalTime time) {

    public Reservation mapToReservation(final Long id) {
        return new Reservation(id, name, date, time);
    }
}
