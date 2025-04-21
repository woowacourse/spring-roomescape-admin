package roomescape.test.utility;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import roomescape.domain.ReservationTime;

public class ReservationTimeTestUtility {

    public static void checkReservationTimeId(long actualId, long expectedId) {
        assertThat(actualId).isEqualTo(expectedId);
    }

    public static void checkReservationTimeFieldWithoutId(ReservationTime actual, ReservationTime expected) {
        assertThat(actual.getStartAt()).isEqualTo(expected.getStartAt());
    }

    public static void checkDeleteReservationTime(List<ReservationTime> reservationTimes, long deletedId) {
        assertThat(reservationTimes)
                .extracting(ReservationTime::getId)
                .doesNotContain(deletedId);
    }
}
