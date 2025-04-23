package roomescape.reservation.model;

import java.time.LocalTime;

public class ReservationTime {

    private final long id;
    private final ReservationTimeDetails startAt;

    public ReservationTime(long id, LocalTime startAt) {
        this.id = id;
        this.startAt = new ReservationTimeDetails(startAt);
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt.startAt();
    }
}
