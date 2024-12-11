package roomescape.fixture;

import static roomescape.fixture.ReservationFixture.BASE_TIME;

import roomescape.domain.Time;
import roomescape.service.dto.TimeRequest;
import roomescape.service.dto.TimeResponse;

public abstract class TimeFixture {

    public static Time entity(int plusHours) {
        return new Time(BASE_TIME.plusHours(plusHours));
    }

    public static TimeRequest request(int plusHours) {
        return new TimeRequest(BASE_TIME.plusHours(plusHours));
    }

    public static TimeResponse response(long id, int plusHours) {
        return new TimeResponse(id, BASE_TIME.plusHours(plusHours));
    }
}
