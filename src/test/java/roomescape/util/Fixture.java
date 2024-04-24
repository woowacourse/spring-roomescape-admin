package roomescape.util;

import roomescape.dto.ReservationTimeRequest;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

public class Fixture {

    public static final String JOJO = "jojo";
    public static final String GAMJA = "gamja";

    public static final String JOJO_DATE = "2024-04-23";
    public static final String GAMJA_DATE = "2024-04-24";

    public static final String JOJO_TIME = "10:00";
    public static final String GAMJA_TIME = "18:00";

    public static final ReservationTime JOJO_RESERVATION_TIME = ReservationTime.of(1L, JOJO_TIME);
    public static final ReservationTime GAMJA_RESERVATION_TIME = ReservationTime.of(2L, GAMJA_TIME);

    public static final Reservation JOJO_RESERVATION = Reservation.of(
        1L,
        JOJO,
        JOJO_DATE,
        JOJO_RESERVATION_TIME
    );
    public static final Reservation GAMJA_RESERVATION = Reservation.of(
        2L,
        GAMJA,
        GAMJA_DATE,
        GAMJA_RESERVATION_TIME
    );

    public static final ReservationTimeRequest JOJO_RESERVATION_TIME_REQUEST = new ReservationTimeRequest(JOJO_TIME);
}
