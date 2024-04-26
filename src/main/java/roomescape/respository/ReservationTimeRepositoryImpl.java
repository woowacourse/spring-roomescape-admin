package roomescape.respository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.reservationtime.ReservationTime;

public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private static final int INITIAL_ID_VALUE = 1;

    private final AtomicLong reservationTimeId = new AtomicLong(INITIAL_ID_VALUE);
    private final Map<Long, ReservationTime> store = new ConcurrentHashMap<>();

    @Override
    public List<ReservationTime> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public ReservationTime findById(long id) {
        return store.get(id);
    }

    @Override
    public ReservationTime add(ReservationTime reservationTime) {
        long id = reservationTimeId.getAndIncrement();
        ReservationTime newReservationTime = new ReservationTime(
                id,
                reservationTime.getStartAt()
        );
        store.put(id, newReservationTime);
        return newReservationTime;
    }

    @Override
    public Boolean exist(long id) {
        return store.containsKey(id);
    }

    @Override
    public void delete(long id) {
        store.remove(id);
    }
}
