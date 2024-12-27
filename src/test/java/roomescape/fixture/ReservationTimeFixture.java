package roomescape.fixture;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationTimeRequest;
import roomescape.service.dto.ReservationTimeResponse;

public abstract class ReservationTimeFixture {

    public static final int INITIAL_RESERVATION_TIME_SIZE = 3;
    public static final long TIME_1_ID = 1;
    public static final long TIME_2_ID = 2;
    public static final long NO_RESERVATION_TIME_ID = 3;

    public static ReservationTime time1() {
        return new ReservationTime(TIME_1_ID, LocalTime.of(10, 0));
    }

    public static ReservationTime time2() {
        return new ReservationTime(TIME_2_ID, LocalTime.of(11, 0));
    }

    public static ReservationTime noReservationTime() {
        return new ReservationTime(NO_RESERVATION_TIME_ID, LocalTime.of(12, 0));
    }

    public static ReservationTime newTimeWithoutId() {
        return new ReservationTime(LocalTime.of(13, 0));
    }

    public static ReservationTime newTime() {
        return new ReservationTime(INITIAL_RESERVATION_TIME_SIZE + 1L, LocalTime.of(13, 0));
    }

    public static ReservationTimeRequest newTimeRequest() {
        return new ReservationTimeRequest(LocalTime.of(13, 0));
    }

    public static ReservationTimeResponse newTimeResponse() {
        return new ReservationTimeResponse(INITIAL_RESERVATION_TIME_SIZE + 1L, LocalTime.of(13, 0));
    }
}
