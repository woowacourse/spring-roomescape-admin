package roomescape.reservationtime.controller;

import java.time.LocalTime;

public class ReservationTimeRequest {

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