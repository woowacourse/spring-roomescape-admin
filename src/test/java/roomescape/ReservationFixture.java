package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.service.ReservationRequest;
import roomescape.service.ReservationResponse;

public class ReservationFixture {

    public static final String NAME = "test name";
    public static final LocalDate BASE_DATE = LocalDate.now();
    public static final LocalTime BASE_TIME = LocalTime.now();

    public static ReservationRequest request(int plusDays) {
        return new ReservationRequest(NAME, BASE_DATE.plusDays(plusDays), BASE_TIME);
    }

    public static ReservationResponse response(long id, int plusDays) {
        return new ReservationResponse(id, NAME, BASE_DATE.plusDays(plusDays), BASE_TIME);
    }

}
