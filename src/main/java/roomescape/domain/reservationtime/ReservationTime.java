package roomescape.domain.reservationtime;

import java.time.LocalTime;
import lombok.Getter;

@Getter
public class ReservationTime {

    private final Long id;
    private final LocalTime startAt;

    private ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    private ReservationTime(LocalTime startAt) {
        this.id = null;
        this.startAt = startAt;
    }

    public static ReservationTime createWithoutId(LocalTime startAt) {
        return new ReservationTime(startAt);
    }

    public static ReservationTime createWithId(Long id, ReservationTime reservationTime) {
        return new ReservationTime(id, reservationTime.getStartAt());
    }
}
