package roomescape.test.utility;

import static org.assertj.core.api.Assertions.assertThat;

import roomescape.domain.Reservation;

public class ReservationTestUtility {

    public static void checkReservation(Reservation actual, Reservation expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void checkReservationId(long actualId, long expectedId) {
        assertThat(actualId).isEqualTo(expectedId);
    }
}
