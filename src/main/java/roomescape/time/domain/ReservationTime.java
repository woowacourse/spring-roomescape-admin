package roomescape.time.domain;

import java.time.LocalTime;
import roomescape.time.dto.ReservationTimeRequest;

public class ReservationTime {

    private final Long id;

    private final LocalTime startAt;

    private ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime create(ReservationTimeRequest reservationTimeRequest) {
        return new ReservationTime(null, reservationTimeRequest.startAt());
    }

    public static ReservationTime create(Long id, LocalTime startAt) {
        return new ReservationTime(id, startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
