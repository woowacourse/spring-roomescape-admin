package roomescape.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeDao;

public class FakeReservationTimeDao implements ReservationTimeDao {

    private final Map<Long, ReservationTime> fakeReservationTimeDB = new HashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<ReservationTime> findAll() {
        return fakeReservationTimeDB.values().stream().toList();
    }

    @Override
    public ReservationTime findById(long id) {
        return fakeReservationTimeDB.get(id);
    }

    @Override
    public long save(ReservationTime reservationTime) {
        long idx = index.getAndIncrement();
        fakeReservationTimeDB.put(idx, new ReservationTime(idx, reservationTime.getStartAt()));
        return idx;
    }

    @Override
    public int deleteById(long id) {
        ReservationTime reservationTime = fakeReservationTimeDB.remove(id);
        if (reservationTime != null) {
            return 1;
        }
        return 0;
    }

    @Override
    public void deleteAll() {
        fakeReservationTimeDB.clear();
    }
}
