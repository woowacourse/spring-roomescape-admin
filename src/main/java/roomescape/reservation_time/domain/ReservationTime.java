package roomescape.reservation_time.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class ReservationTime {

    private final ReservationTimeId id;
    private final LocalTime value;

    public static ReservationTime of(final ReservationTimeId id, final LocalTime time) {
        return new ReservationTime(id, time);
    }
}
