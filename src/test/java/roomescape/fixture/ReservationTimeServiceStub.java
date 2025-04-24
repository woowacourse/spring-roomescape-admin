package roomescape.fixture;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

public class ReservationTimeServiceStub extends ReservationTimeService {

    private final Map<Long, ReservationTime> times = new HashMap<>();

    public ReservationTimeServiceStub() {
        super(null);
        times.put(1L, new ReservationTime(1L, LocalTime.of(10, 0)));
    }

    @Override
    public ReservationTime getBy(Long timeId) {
        return times.get(timeId);
    }
}
