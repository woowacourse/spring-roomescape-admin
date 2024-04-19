package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationTimeRequest;

public class ReservationTime {
    private final Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime of(Long id, ReservationTimeRequest reservationTimeRequest) {
        return new ReservationTime(id, reservationTimeRequest.startAt());
    }

    public static ReservationTime of(long id, ReservationRequest reservationRequest) {
        return new ReservationTime(id, reservationRequest.time());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
