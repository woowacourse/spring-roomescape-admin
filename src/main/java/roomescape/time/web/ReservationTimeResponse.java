package roomescape.time.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.time.ReservationTime;

public class ReservationTimeResponse {
    private final long id;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime startAt;

    public ReservationTimeResponse(ReservationTime reservationTime) {
        this.id = reservationTime.getId();
        this.startAt = reservationTime.getStartAt();
    }

    public long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
