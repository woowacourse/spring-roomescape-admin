package roomescape.model;

import java.time.LocalTime;
import roomescape.exception.reservationTime.ReservationTimeFieldRequiredException;

public class ReservationTime {
    private final LocalTime startAt;

    public ReservationTime(LocalTime startAt) {
        validateStartAt(startAt);
        this.startAt = startAt;
    }

    private void validateStartAt(LocalTime startAt) {
        if (startAt == null) {
            throw new ReservationTimeFieldRequiredException("시작 시간");
        }
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
