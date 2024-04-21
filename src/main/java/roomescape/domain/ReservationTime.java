package roomescape.domain;

import java.time.LocalTime;
import roomescape.dto.ReservationTimeRequest;

public class ReservationTime {
    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime of(Long id, ReservationTimeRequest reservationTimeRequest) {
        return new ReservationTime(id, reservationTimeRequest.startAt());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
