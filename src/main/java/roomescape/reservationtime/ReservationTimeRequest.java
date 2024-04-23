package roomescape.reservationtime;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public class ReservationTimeRequest {

    private final LocalTime startAt;

    public ReservationTimeRequest(@JsonProperty("startAt") final LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}