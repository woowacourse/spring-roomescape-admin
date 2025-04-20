package roomescape.test.fixture;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public class ReservationFixture {

    public static Reservation create(String name) {
        return Reservation.createWithoutId(
                name, LocalDate.now().plusDays(1), LocalTime.now().withNano(0));
    }
}
