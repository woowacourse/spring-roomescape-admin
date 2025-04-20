package roomescape.test.utility;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import roomescape.domain.Reservation;

public class ReservationsTestUtility {

    public static void checkDeleteReservation(List<Reservation> reservations, long deletedId) {
        assertThat(reservations)
                .extracting(Reservation::getId)
                .doesNotContain(deletedId);
    }
}
