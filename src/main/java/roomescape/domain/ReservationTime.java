package roomescape.domain;

import java.time.LocalTime;
import roomescape.dto.validation.ValidReservationTime;

@ValidReservationTime
public class ReservationTime {

    private final Long id;
    private final LocalTime time;

    public ReservationTime(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
