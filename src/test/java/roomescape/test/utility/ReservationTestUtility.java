package roomescape.test.utility;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import roomescape.domain.Reservation;

public class ReservationTestUtility {

    public static void checkReservation(Reservation actual, Reservation expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void checkReservationField(Reservation actual, String name, String date, String time) {
        assertAll(
                () -> assertThat(actual).extracting(Reservation::getName).isEqualTo(name),
                () -> assertThat(actual).extracting(Reservation::getDate).isEqualTo(date),
                () -> assertThat(actual).extracting(Reservation::getTime).isEqualTo(time)
        );
    }
}
