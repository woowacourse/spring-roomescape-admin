package roomescape;

import java.time.LocalTime;

public class ReservationTime {

    private LocalTime startAt;

    public ReservationTime(LocalTime startAt) {
        this.startAt = startAt;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
