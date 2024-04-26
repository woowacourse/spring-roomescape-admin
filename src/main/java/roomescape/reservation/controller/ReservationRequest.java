package roomescape.reservation.controller;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ReservationRequest {

    @NotEmpty
    private final String name;
    @FutureOrPresent
    private final LocalDate date;
    @NotNull
    private final Long timeId;

    public ReservationRequest(final String name, final LocalDate date, final Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }
}