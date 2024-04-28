package roomescape.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.ReservationTime;

public class MemoryReservationTimeRepository implements ReservationTimeRepository {

    private final Map<Long, ReservationTime> reservationTimes;
    private final AtomicLong index;

    public MemoryReservationTimeRepository() {
        this.reservationTimes = new HashMap<>();
        this.index = new AtomicLong(1);
    }

    @Override
    public ReservationTime save(ReservationTime time) {
        long id = index.getAndIncrement();
        reservationTimes.put(id, new ReservationTime(id, time.getStartAt()));
        return findById(id);
    }

    @Override
    public ReservationTime findById(Long id) {
        return reservationTimes.get(id);
    }

    @Override
    public List<ReservationTime> findAll() {
        return new ArrayList<>(reservationTimes.values());
    }

    @Override
    public void delete(Long id) {
        reservationTimes.remove(id);
    }
}
