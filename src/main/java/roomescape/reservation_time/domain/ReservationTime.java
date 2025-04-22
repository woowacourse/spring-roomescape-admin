package roomescape.reservation_time.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReservationTime {

    private final ReservationTimeId id;
    private final LocalTime value;

    public static ReservationTime of(final ReservationTimeId id, final LocalTime time) {
        return new ReservationTime(id, time);
    }
}
