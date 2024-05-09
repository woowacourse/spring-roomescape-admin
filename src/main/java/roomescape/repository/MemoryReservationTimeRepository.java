package roomescape.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.ReservationTime;

public class MemoryReservationTimeRepository implements ReservationTimeRepository {

    private static final MemoryReservationTimeRepository INSTANCE
        = new MemoryReservationTimeRepository(new HashMap<>(), new AtomicLong(1));

    private final Map<Long, ReservationTime> reservationTimes;
    private final AtomicLong index;

    private MemoryReservationTimeRepository(Map<Long, ReservationTime> reservationTimes, AtomicLong index) {
        this.reservationTimes = reservationTimes;
        this.index = index;
    }

    public static MemoryReservationTimeRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public ReservationTime save(ReservationTime time) {
        long id = index.getAndIncrement();
        reservationTimes.put(id, new ReservationTime(id, time.getStartAt()));
        return findById(id);
    }

    @Override
    public ReservationTime findById(Long id) {
        if (reservationTimes.containsKey(id)) {
            return reservationTimes.get(id);
        } else {
            throw new IllegalArgumentException("해당 ID의 시간이 존재하지 않습니다.");
        }
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
