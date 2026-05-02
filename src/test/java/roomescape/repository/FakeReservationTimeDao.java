package roomescape.repository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import roomescape.domain.ReservationTime;

public class FakeReservationTimeDao implements ReservationTimeDao {

    private final Map<Long, ReservationTime> storage = new HashMap<>();
    private long sequence = 1L;

    @Override
    public List<ReservationTime> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public long insert(LocalTime startAt) {
        long id = sequence++;
        storage.put(id, new ReservationTime(id, startAt));
        return id;
    }

    @Override
    public void deleteById(long timeId) {
        storage.remove(timeId);
    }

    @Override
    public ReservationTime findById(long timeId) {
        return storage.get(timeId);
    }
}
