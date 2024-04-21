package roomescape.reservation.domain;

import java.time.LocalTime;
import roomescape.reservation.dto.request.ReservationTimeRequest;

public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime from(ReservationTimeRequest reservationTimeRequest) {
        return new ReservationTime(null, reservationTimeRequest.startAt());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
