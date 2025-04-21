package roomescape.test.utility;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.test.utility.ReservationTimeTestUtility.checkReservationTimeFieldWithoutId;
import static roomescape.test.utility.ReservationTimeTestUtility.checkReservationTimeId;

import java.util.List;
import roomescape.domain.Reservation;

public class ReservationTestUtility {

    public static void checkReservationId(long actualId, long expectedId) {
        assertThat(actualId).isEqualTo(expectedId);
    }

    public static void checkReservationFieldWithoutId(Reservation actual, Reservation expected) {
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getDate()).isEqualTo(expected.getDate());
        checkReservationTimeId(actual.getTime().getId(), expected.getTime().getId());
        checkReservationTimeFieldWithoutId(actual.getTime(), expected.getTime());
    }

    public static void checkDeleteReservation(List<Reservation> reservations, long deletedId) {
        assertThat(reservations)
                .extracting(Reservation::getId)
                .doesNotContain(deletedId);
    }
}
