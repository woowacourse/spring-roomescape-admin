package roomescape.dto;

import java.time.LocalTime;

public class ReservationTimeRequest {

    private final LocalTime startAt;

    private ReservationTimeRequest(LocalTime startAt) {
        this.startAt = startAt;
    }

    public static ReservationTimeRequest of(LocalTime startAt) {
        return new ReservationTimeRequest(startAt);
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
