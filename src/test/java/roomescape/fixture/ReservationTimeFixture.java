package roomescape.fixture;

import static roomescape.fixture.ReservationFixture.BASE_TIME;

import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationTimeRequest;
import roomescape.service.dto.ReservatonTimeResponse;

public abstract class ReservationTimeFixture {

    public static ReservationTime entity(int plusHours) {
        return new ReservationTime(BASE_TIME.plusHours(plusHours));
    }

    public static ReservationTimeRequest request(int plusHours) {
        return new ReservationTimeRequest(BASE_TIME.plusHours(plusHours));
    }

    public static ReservatonTimeResponse response(long id, int plusHours) {
        return new ReservatonTimeResponse(id, BASE_TIME.plusHours(plusHours));
    }
}
