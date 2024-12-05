package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;
import roomescape.service.ReservationRequest;
import roomescape.service.ReservationResponse;

public class ReservationFixture {

    public static final String NAME = "test name";
    public static final LocalDate BASE_DATE = LocalDate.now();
    public static final LocalTime BASE_TIME = LocalTime.now();

    public static Reservation entity(int plusDays, int plusHours) {
        return new Reservation(NAME, BASE_DATE.plusDays(plusDays), BASE_TIME.plusHours(plusHours));
    }

    public static ReservationRequest request(int plusDays, int plusHours) {
        return new ReservationRequest(NAME, BASE_DATE.plusDays(plusDays), BASE_TIME.plusHours(plusHours));
    }

    public static ReservationResponse response(long id, int plusDays, int plusHours) {
        return new ReservationResponse(id, NAME, BASE_DATE.plusDays(plusDays), BASE_TIME.plusHours(plusHours));
    }

}
