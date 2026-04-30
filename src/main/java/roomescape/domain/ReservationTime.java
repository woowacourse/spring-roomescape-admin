package roomescape.domain;

import java.time.LocalTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReservationTime {

    private static final long PENDING_RESERVATION_TIME_ID = -1L;

    private final long id;
    private final LocalTime startAt;

    public static ReservationTime none() {
        return new ReservationTime(PENDING_RESERVATION_TIME_ID, LocalTime.MIDNIGHT);
    }

    public static ReservationTime pending(LocalTime startAt) {
        return new ReservationTime(PENDING_RESERVATION_TIME_ID, startAt);
    }

    public static ReservationTime create(long id, LocalTime startAt) {
        return new ReservationTime(id, startAt);
    }

    public LocalTime startAt() {
        return startAt;
    }

    public long id() {
        return id;
    }
}
