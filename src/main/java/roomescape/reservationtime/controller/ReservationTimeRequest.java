package roomescape.reservationtime.controller;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class ReservationTimeRequest {

    @NotNull
    private final LocalTime startAt;

    private ReservationTimeRequest() {
        startAt = null;
    }

    private ReservationTimeRequest(final LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}