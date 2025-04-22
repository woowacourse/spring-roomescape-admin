package roomescape.domain.fixture;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public class ReservationFixture {

    public static final Reservation RESERVATION_1_KIM_2025_04_21_10_00 = new Reservation(1L, "kim",
            LocalDate.of(2025, 4, 21),
            LocalTime.of(10, 0));
    public static final Reservation RESERVATION_2_Lee_2025_04_22_10_00 = new Reservation(2L, "lee",
            LocalDate.of(2025, 4, 22),
            LocalTime.of(10, 0));
}
