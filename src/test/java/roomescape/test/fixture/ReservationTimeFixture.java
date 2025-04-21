package roomescape.test.fixture;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public class ReservationTimeFixture {

    public static ReservationTime createReservationTime(LocalTime time) {
        return ReservationTime.createWithoutId(time.withNano(0));
    }

    public static ReservationTime createReservationTime(Long id, LocalTime time) {
        return new ReservationTime(id, time);
    }
}
