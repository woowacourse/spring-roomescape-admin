package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.model.ReservationTime;

public class ReservationTimeMemoryRepository implements ReservationTimeRepository{
    private static final int ZERO_RESERVATION = 0;
    private static final int ONE_RESERVATION = 1;
    
    Map<Long, ReservationTime> reservationTimes = new HashMap<>();
    AtomicLong id = new AtomicLong(1);

    @Override
    public Long createTime(ReservationTime reservationTime) {
        reservationTimes.put(id.incrementAndGet(), reservationTime);
        return id.longValue();
    }

    @Override
    public List<ReservationTime> readTimes() {
        return reservationTimes.values().stream().toList();
    }

    @Override
    public int deleteTime(Long id) {
        if (reservationTimes.remove(id) == null) {
            return ZERO_RESERVATION;
        }
        return ONE_RESERVATION;
    }
}
