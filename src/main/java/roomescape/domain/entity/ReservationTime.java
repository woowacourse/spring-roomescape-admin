package roomescape.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public final class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    @JsonCreator
    public ReservationTime(
            @JsonProperty("id") Long id,
            @JsonProperty("start_at") LocalTime startAt
    ) {
        this.id = id;
        this.startAt = startAt;
    }

    public ReservationTime(LocalTime startAt) {
        this.id = null;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public boolean isSameReservationTime(ReservationTime reservationTime) {
        return startAt.equals(reservationTime.getStartAt());
    }
}
