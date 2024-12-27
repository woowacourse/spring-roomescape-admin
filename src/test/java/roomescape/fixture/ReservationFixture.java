package roomescape.fixture;

import static roomescape.fixture.ReservationTimeFixture.TIME_2_ID;
import static roomescape.fixture.ThemeFixture.THEME_2_ID;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.service.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;

public abstract class ReservationFixture {

    public static final int INITIAL_RESERVATION_SIZE = 3;
    public static final long RESERVATION_1_ID = 1;
    public static final long RESERVATION_2_ID = 2;
    public static final long RESERVATION_3_ID = 3;

    public static Reservation reservation1() {
        return new Reservation(RESERVATION_1_ID, "reservator1", LocalDate.of(2100, 12, 1),
                ReservationTimeFixture.time1(), ThemeFixture.theme1());
    }

    public static Reservation reservation2() {
        return new Reservation(RESERVATION_2_ID, "reservator2", LocalDate.of(2100, 12, 1),
                ReservationTimeFixture.time1(), ThemeFixture.theme2());
    }

    public static Reservation reservation3() {
        return new Reservation(RESERVATION_3_ID, "reservator3", LocalDate.of(2100, 12, 1),
                ReservationTimeFixture.time2(), ThemeFixture.theme1());
    }

    public static Reservation newReservationWithoutId() {
        return new Reservation("reservator4", LocalDate.of(2100, 12, 1), ReservationTimeFixture.time2(),
                ThemeFixture.theme2());
    }

    public static Reservation newReservation() {
        return new Reservation(INITIAL_RESERVATION_SIZE + 1L, "reservator4", LocalDate.of(2100, 12, 1),
                ReservationTimeFixture.time2(), ThemeFixture.theme2());
    }

    public static ReservationRequest newRequest() {
        return new ReservationRequest("reservator4", LocalDate.of(2100, 12, 1), TIME_2_ID, THEME_2_ID);
    }

    public static ReservationRequest badRequest() {
        return new ReservationRequest("reservator4", LocalDate.now().minusDays(1), TIME_2_ID, THEME_2_ID);
    }

    public static String newRequestString() {
        return """
                {
                    "date": "2100-12-01",
                    "name": "reservatior4",
                    "timeId": 2,
                    "themeId": 2,
                }
                """;
    }

    public static ReservationResponse newResponse() {
        return new ReservationResponse(newReservation());
    }
}
