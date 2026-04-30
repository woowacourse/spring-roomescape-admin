package roomescape.domain;

import java.time.LocalTime;
import lombok.Getter;

@Getter
public class ReservationTime {
    private final Long id;
    private final LocalTime reservationTime;

    public ReservationTime(Long id, LocalTime reservationTime) {
        this.id = id;
        this.reservationTime = reservationTime;
    }

    public ReservationTime(LocalTime reservationTime) {
        this(null, reservationTime);
    }
}
