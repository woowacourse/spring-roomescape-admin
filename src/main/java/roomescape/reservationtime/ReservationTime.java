package roomescape.reservationtime;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    @JsonFormat(pattern = "HH:mm")
    public LocalTime getStartAt() {
        return startAt;
    }
}