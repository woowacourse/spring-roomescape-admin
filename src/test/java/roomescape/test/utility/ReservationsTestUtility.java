package roomescape.test.utility;

import static org.assertj.core.api.Assertions.assertThat;

import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

public class ReservationsTestUtility {

    public static void checkDeleteReservation(Reservations reservations, long deletedId) {
        assertThat(reservations.getReservations())
                .extracting(Reservation::getId)
                .doesNotContain(deletedId);
    }
}
